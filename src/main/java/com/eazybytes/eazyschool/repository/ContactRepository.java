package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.rowmappers.ContactRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/*
@Repository stereotype annotation is used to add a bean of this class
type to the Spring context and indicate that given Bean is used to perform
DB related operations and
* */
@Repository
public interface ContactRepository extends PagingAndSortingRepository<Contact, Integer> {
    // comment all code due to reason of shifting jdbcTemplate to spring data JPA

//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public ContactRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public int saveContactMsg(Contact contact){
//        String sql = "insert into contact_msg (name,mobile_num,email,subject,message,status," +
//                "created_at,created_by) values (?,?,?,?,?,?,?,?)";
//        return jdbcTemplate.update(sql,contact.getName(),contact.getMobileNum(),
//                contact.getEmail(),contact.getSubject(),contact.getMessage(),
//                contact.getStatus(),contact.getCreatedAt(),contact.getCreatedBy());
//    }
//
//    public List<Contact> findMsgsWithStatus(String status) {
//        String sql = "select * from contact_msg where status = ?";
//        return jdbcTemplate.query(sql,new PreparedStatementSetter() {
//            public void setValues(PreparedStatement preparedStatement) throws SQLException {
//                preparedStatement.setString(1, status);
//            }
//        },new ContactRowMapper());
//    }
//
//    public int updateMsgStatus(int contactId, String status,String updatedBy) {
//        String sql = "update contact_msg set status = ?, updated_by = ?,updated_at =? where contact_id = ?";
//        return jdbcTemplate.update(sql,new PreparedStatementSetter() {
//            public void setValues(PreparedStatement preparedStatement) throws SQLException {
//                preparedStatement.setString(1, status);
//                preparedStatement.setString(2, updatedBy);
//                preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
//                preparedStatement.setInt(4, contactId);
//            }
//        });
//    }

    List<Contact> findByStatus(String status);
   // JPQL in which we used entity name and field as column
   // @Query("SELECT c FROM Contact c where c.status = :status")
   // native SQL query we used actual table and column name
    @Query(value = "SELECT * FROM contact_msg  where status = :status", nativeQuery = true)
    Page<Contact> findByStatus(String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.status = ?1 WHERE c.contactId = ?2")
    int updateStatusById(String status, int id);

    Page<Contact> findOpenMsgs(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    int updateMsgStatus(String status, int id);

    @Query(nativeQuery = true)
    Page<Contact> findOpenMsgsNative(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query(nativeQuery = true)
    int updateMsgStatusNative(String status, int id);
}
