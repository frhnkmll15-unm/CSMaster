package com.juaracoding.CSmaster.repo;
/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author Farhan a.k.a Muhammad Farhan Kamil
Java Developer
Created On 15/03/2023 12:44
@Last Modified 15/03/2023 12:44
Version 1.0
*/

import com.juaracoding.CSmaster.model.Complain;
import com.juaracoding.CSmaster.model.Demo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplainRepo extends JpaRepository<Complain,Long> {

    Page<Complain> findByIsDelete(Pageable page , byte byteIsDelete);
    Page<Complain> findByIsDeleteAndNamaBarangContainsIgnoreCase(Pageable page , byte byteIsDelete, String values);
    Page<Complain> findByIsDeleteAndIdComplainContainsIgnoreCase(Pageable page , byte byteIsDelete, Long values);

}
