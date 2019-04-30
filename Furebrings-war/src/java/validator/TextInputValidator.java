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
@FacesValidator("textInputValidator")
public class TextInputValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        if(!doValidation()){
            return;
        }
        
        String str = (String)value;        
        
        //if(!str.matches("([a-zA-ZåäöÅÄÖ){1,30}")){
        if(!str.matches("(([a-zA-ZåäöÅÄÖ])+(([ ]|[-])([a-zA-ZåäöÅÄÖ])+)*){1,30}")){
            String message = "Bara alfabet och mellanslag är tillåtna.Max antal tecken är 30.";                        
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
