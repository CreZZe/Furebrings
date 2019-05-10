
package entities;

import java.io.Serializable;
import static javax.persistence.CascadeType.PERSIST;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String mail;
    private String pass;
    private String accRole;
    
    @OneToOne(cascade=PERSIST)
    private Customer customer;

    public Account() {
    }

    public Account(String mail, String pass) {
        this.mail = mail;
        this.pass = pass;
    }

    public Account(String mail, String pass, String accRole) {
        this(mail, pass, accRole, null);
    }

    public Account(String mail, String pass, String accRole, Customer customer) {
        this.mail = mail;
        this.pass = pass;
        this.accRole = accRole;
        this.customer = customer;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAccRole() {
        return accRole;
    }

    public void setAccRole(String accRole) {
        this.accRole = accRole;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Account[ id=" + id + " ]";
    }

}
