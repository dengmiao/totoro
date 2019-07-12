package com.cn.miao.base;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @title: BaseService
 * @description:
 * @author: dengmiao
 * @create: 2019-07-12 14:52
 **/
@FunctionalInterface
public interface BaseService<Entity, ID extends Serializable> {

    /**
     * repository
     * @return
     */
    BaseRepository<Entity, ID> getRepository();

    /**
     * 保存
     * @param entity
     * @return
     */
    default Entity create(Entity entity) {
        return getRepository().save(entity);
    }

    /**
     * 根据ID获取返回实体
     * @param id
     * @return
     */
    default Entity get(ID id) {
        return getRepository().getOne(id);
    }

    /**
     * 根据ID获取
     * @param id
     * @return
     */
    default Optional<Entity> retrieve(ID id) {
        return getRepository().findById(id);
    }

    /**
     * Specification
     * @param spec
     * @return
     */
    default Optional<Entity> retrieve(Specification<Entity> spec) {
        return getRepository().findOne(spec);
    }

    /**
     * Example
     * @param example
     * @return
     */
    default Optional<Entity> retrieve(Example example) {
        return getRepository().findOne(example);
    }

    /**
     * 修改
     * @param entity
     * @return
     */
    default Entity update(Entity entity) {
        return getRepository().saveAndFlush(entity);
    }

    /**
     * 批量保存与修改
     * @param entities
     * @return
     */
    default Iterable<Entity> saveOrUpdateAll(Iterable<Entity> entities) {
        return getRepository().saveAll(entities);
    }

    /**
     * 删除
     * @param entity
     */
    default void delete(Entity entity) {
        getRepository().delete(entity);
    }

    /**
     * 根据Id删除
     * @param id
     */
    default void delete(ID id) {
        getRepository().deleteById(id);
    }

    /**
     * 批量删除
     * @param entities
     */
    default void delete(Iterable<Entity> entities) {
        getRepository().deleteAll(entities);
    }

    /**
     * 清空缓存，提交持久化
     */
    default void flush() {
        getRepository().flush();
    }

    /**
     * 根据条件查询获取
     * @param spec
     * @return
     */
    default List<Entity> list(Specification<Entity> spec) {
        return getRepository().findAll(spec);
    }

    /**
     * Example
     * @param example
     * @return
     */
    default List<Entity> list(Example example) {
        return getRepository().findAll(example);
    }

    /**
     * 获取所有列表
     * @return
     */
    default List<Entity> list() {
        return getRepository().findAll();
    }

    /**
     * 分页获取
     * @param pageable
     * @return
     */
    default Page<Entity> page(Pageable pageable){
        return getRepository().findAll(pageable);
    }

    /**
     * 根据查询条件分页获取
     * @param spec
     * @param pageable
     * @return
     */
    default Page<Entity> page(Specification<Entity> spec, Pageable pageable) {
        return getRepository().findAll(spec, pageable);
    }

    /**
     * 获取总数
     * @return
     */
    default Long count() {
        return getRepository().count();
    }

    /**
     * 获取查询条件的结果数
     * @param spec
     * @return
     */
    default long count(Specification<Entity> spec) {
        return getRepository().count(spec);
    }

}
