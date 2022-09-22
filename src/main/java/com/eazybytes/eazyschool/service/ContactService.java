package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.config.EazySchoolProps;
import com.eazybytes.eazyschool.constants.EazySchoolConstants;
import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
/*
@Slf4j, is a Lombok-provided annotation that will automatically generate an SLF4J
Logger static property in the class at compilation time.
* */


@Slf4j
@Service
//@RequestScope
//@SessionScope
// @ApplicationScope
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    EazySchoolProps eazySchoolProps;


   // private int counter = 0;

    public ContactService(){
        System.out.println("Contact Service Bean initialized");
    }

    public boolean saveMessageDetails(Contact contact){
      //  boolean isSaved = true;
      //  log.info(contact.toString());
        boolean isSaved = false;
        contact.setStatus(EazySchoolConstants.OPEN);
        // now this responsibility is spring auditing feature
//        contact.setCreatedBy(EazySchoolConstants.ANONYMOUS);
//        contact.setCreatedAt(LocalDateTime.now());
        // comment code due to reason using spring data Jpa replacing JdbcTemplate
//        int result = contactRepository.saveContactMsg(contact);
        Contact saveContact = contactRepository.save(contact);
//        if(result>0) {
        if (null != saveContact && saveContact.getContactId()>0) {
            isSaved = true;
        }
        return isSaved;
    }

    public Page<Contact> findMsgsWithOpenStatus(int pageNum,String sortField, String sortDir){
       // int pageSize = 5;
        // now getting values from props class
        int pageSize = eazySchoolProps.getPageSize();
        if(null!=eazySchoolProps.getContact() && null!=eazySchoolProps.getContact().get("pageSize")){
            pageSize = Integer.parseInt(eazySchoolProps.getContact().get("pageSize").trim());
        }

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
//        Page<Contact> msgPage = contactRepository.findByStatus(
//                EazySchoolConstants.OPEN,pageable);

        // revert due to dynamic sorting not working in @NamedNativeQuery
   //     Page<Contact> msgPage = contactRepository.findOpenMsgs(
        Page<Contact> msgPage = contactRepository.findByStatusWithQuery(
                EazySchoolConstants.OPEN,pageable);
        return msgPage;
    }

    public boolean updateMsgStatus(int contactId){
        boolean isUpdated = false;
        // comment code due to custom update queries
//        Optional<Contact> contact = contactRepository.findById(contactId);
//        contact.ifPresent(contact1 -> {
//            contact1.setStatus(EazySchoolConstants.CLOSE);
            // now this responsibility is spring auditing feature
//            contact1.setUpdatedBy(updatedBy);
//            contact1.setUpdatedAt(LocalDateTime.now());
      //  });
//        Contact updatedContact = contactRepository.save(contact.get());
//        if(null != updatedContact && updatedContact.getUpdatedBy()!=null) {
//            isUpdated = true;
//        }

//        int result = contactRepository.updateMsgStatus(contactId,EazySchoolConstants.CLOSE, updatedBy);
//        if(result>0) {
//            isUpdated = true;
//        }
       // int rows = contactRepository.updateStatusById(EazySchoolConstants.CLOSE,contactId);
        int rows = contactRepository.updateMsgStatus(EazySchoolConstants.CLOSE,contactId);
        if (rows > 0) {
            isUpdated = true;
        }
        return isUpdated;
    }

//    public int getCounter() {
//        return counter;
//    }
//    public void setCounter(int counter) {
//        this.counter = counter;
//    }
}
