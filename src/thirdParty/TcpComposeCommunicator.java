package thirdParty;

import scheduling.Alternatives;
import scheduling.Job;
import scheduling.Response;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by maheedhar on 6/14/16.
 */
public interface TcpComposeCommunicator {

    public Set<Job> getCoverage(Alternatives alternatives);

    public HashMap<Job, HashSet<Response>> getOriginalResponses();

}
