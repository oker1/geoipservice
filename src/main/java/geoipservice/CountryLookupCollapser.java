package geoipservice;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;

import java.util.Collection;
import java.util.List;

/**
 * @author Zsolt Takacs <zsolt@takacs.cc>
 */
public class CountryLookupCollapser extends HystrixCollapser<List<String>, String, String> {
    private final String key;

    public CountryLookupCollapser(String key) {
        super(Setter.withCollapserKey(null).andScope(Scope.GLOBAL));
        this.key = key;
    }

    @Override
    public String getRequestArgument() {
        return key;
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, String>> requests) {
        return new GeoIpCountryCommand(requests);
    }

    @Override
    protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, String>> requests) {
        int count = 0;
        for (CollapsedRequest<String, String> request : requests) {
            request.setResponse(batchResponse.get(count++));
        }
    }
}
