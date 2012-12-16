package geoipservice;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * @author oker <zsolt@takacs.cc>
 */
public class GeoIpCountryCommand extends HystrixCommand<String> {
    protected GeoIpCountryCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("GeoIp"));
    }

    @Override
    protected String run() {
        return "Hello geoip world!";
    }
}
