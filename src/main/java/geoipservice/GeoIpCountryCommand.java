package geoipservice;

import com.maxmind.geoip.LookupService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.io.File;
import java.io.IOException;

/**
 * @author oker <zsolt@takacs.cc>
 */
public class GeoIpCountryCommand extends HystrixCommand<String> {
    private String ip;

    protected GeoIpCountryCommand(String ip) {
        super(HystrixCommandGroupKey.Factory.asKey("GeoIp"));
        this.ip = ip;
    }

    @Override
    protected String run() {
        try {
            if (ip != null) {
                return new LookupService(new File("/usr/local/share/GeoIP/GeoIP.dat"))
                        .getCountry(ip).getCode();
            } else {
                throw new RuntimeException("Ip parameter missing!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
