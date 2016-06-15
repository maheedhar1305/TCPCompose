package thirdParty;

import scheduling.Alternatives;
import scheduling.Job;

import java.util.Set;

/**
 * Created by maheedhar on 6/14/16.
 */
public interface TcpComposeCommunicator {

    public Set<Job> getCoverage(Alternatives alternatives);

}
