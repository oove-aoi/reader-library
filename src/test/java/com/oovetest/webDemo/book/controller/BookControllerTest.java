package com.oovetest.webDemo.book.controller;

import com.oovetest.webDemo.book.dto.BookRequest;
import com.oovetest.webDemo.book.dto.BookResponse;
import com.oovetest.webDemo.book.dto.BookSimpleResponse;
import com.oovetest.webDemo.book.service.BookSearchCondition;
import com.oovetest.webDemo.book.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;


public class BookControllerTest {

}
