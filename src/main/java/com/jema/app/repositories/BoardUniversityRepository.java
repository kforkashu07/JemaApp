/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 21-Apr-2023
*
*/

package com.jema.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.BoardUniversity;

@Repository
public interface BoardUniversityRepository extends JpaRepository<BoardUniversity, Long> {

}
