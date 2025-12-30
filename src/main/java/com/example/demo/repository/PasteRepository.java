package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Paste;

public interface PasteRepository extends JpaRepository<Paste, String>{

}
