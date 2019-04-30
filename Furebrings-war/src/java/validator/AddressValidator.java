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
@FacesValidator("addressValidator")
public class AddressValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        if(!doValidation()){
            return;
        }
        
        String str = (String)value;        
        
        if(!str.matches("([a-zA-ZåäöÅÄÖ:])+(([ ]|[-])(([0-9])|([a-zA-ZåäöÅÄÖ:]))+)*")){
            String message = "Alfabet, siffror, mellanslag och vissa tecken(:-) är tillåtna. ";
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
