package scheduling;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by maheedhar on 6/12/16.
 */
//This is a hack..This class should be in the IRS side actually
public class Response {

    private OWLNamedIndividual responseIRI;
    private Set<IRI> requiredResourcesSet;
    private HashMap<String,String> resourcesValueMap;
    private IRI typeOfResponse;
    private HashMap<String,String> attributes;

    public Response(OWLNamedIndividual responseIRI, Set<IRI> requiredResourcesSet, IRI typeOfIndividual){
        this.responseIRI = responseIRI;
        this.requiredResourcesSet = requiredResourcesSet;
        typeOfResponse = typeOfIndividual;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public IRI getTypeOfResponse() {
        return typeOfResponse;
    }

    public void setTypeOfResponse(IRI typeOfResponse) {
        this.typeOfResponse = typeOfResponse;
    }

    public IRI getIRI(){
        return responseIRI.getIRI();
    }

    public OWLNamedIndividual getResponseIndividual(){
        return responseIRI;
    }

    public void setResourcesValueMap(HashMap<String,String> map){
        resourcesValueMap = new HashMap<>(map);
    }

    public HashMap<String, String> getResourcesValueMap() {
        return resourcesValueMap;
    }

    //This method returns 1 for all the objects and true comparison will be done in the equals() method. No disadvantage seen
    public int hashCode(){
        return 1;
    }

    /*This method plays a crucial role in the ResponseInvertedMatrix.add() method
    * Since that class uses a Hashmap to store all the weaknesses that are addressed by the same weakness,
    * the map needed someway to compare a response and tell if its a duplicate or not. This equals method does that
    * It sees if the response is of the same type and has the same resource values. If so, then it determines both responses to
    * be equal and thus helps build that hashmap
    */
    public boolean equals(Object obj) {
        try {
            Response incoming = (Response)obj;
            if(!this.typeOfResponse.equals(incoming.getTypeOfResponse())){
                return false;
            }
            for (IRI resource : requiredResourcesSet) {
                if (!this.resourcesValueMap.get(resource.toString()).equals(incoming.getResourcesValueMap().get(resource.toString()))) {
                    return false;
                }
            }
            return true;
        }catch (NullPointerException e){
            System.out.println("Wrong map");
            System.out.println(e);
            return false;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public String toString(){
        return responseIRI.toString();
    }
}
