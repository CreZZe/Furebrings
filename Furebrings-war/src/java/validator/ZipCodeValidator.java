package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author isami
 */
@FacesValidator("zipCodeValidator")
public class ZipCodeValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        if(!doValidation()){
            return;
        }
        
        String str = (String)value;  
        String message = "Postnummer ska vara 5 siffror.";
        
        try{
            int zipcode = Integer.parseInt(str);

            if(zipcode < 10000 || zipcode > 97999){
                                        
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
            }
        } catch (NumberFormatException e){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        }
    }
    
    private boolean doValidation(){
        String skipValidator = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("skipValidator");
        if(skipValidator != null && skipValidator.equalsIgnoreCase("true")){
            return false;
        }
        return true;     
    }
    
}
