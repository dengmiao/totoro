package com.cn.miao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @title: BaseRepository
 * @description:
 * @author: dengmiao
 * @create: 2019-07-12 15:50
 **/
@NoRepositoryBean
public interface BaseRepository <E, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {
}
