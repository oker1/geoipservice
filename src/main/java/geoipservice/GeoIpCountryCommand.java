package geoipservice;

import com.maxmind.geoip.LookupService;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author oker <zsolt@takacs.cc>
 */
public class GeoIpCountryCommand extends HystrixCommand<List<String>> {
    private static final Logger logger = LoggerFactory.getLogger(GeoIpCountryCommand.class);

    private final Collection<HystrixCollapser.CollapsedRequest<String, String>> requests;

    protected GeoIpCountryCommand(Collection<HystrixCollapser.CollapsedRequest<String, String>> requests) {
        super(HystrixCommandGroupKey.Factory.asKey("GeoIp"));
        this.requests = requests;
    }

    @Override
    protected List<String> run() {
        ArrayList<String> response = new ArrayList<String>();
        try {
            final LookupService lookupService = new LookupService(new File("/usr/local/share/GeoIP/GeoIP.dat"));
            for (HystrixCollapser.CollapsedRequest<String, String> request : requests) {
                String ip = request.getArgument();
                if (ip != null) {
                    response.add(
                            lookupService
                                    .getCountry(ip).getCode()
                    );
                } else {
                    response.add(null);
                }
            }
        } catch (IOException e) {
        }

        logger.info("Collapsed command count: " + requests.size());

        return response;
    }
}
