package com.jun.spring_practice.user.sqlservice;

import org.springframework.context.annotation.Import;

import com.jun.spring_practice.SqlServiceContext;

@Import(value = SqlServiceContext.class)
public @interface EnableSqlService {
}
