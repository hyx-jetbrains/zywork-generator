<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="top.zywork.dao.{zywork.beanName}{zywork.daoSuffix}">

    <sql id="select_columns">
        {zywork.selectColumns}
    </sql>

    <sql id="query_where_clause">
        {zywork.queryWhereClause}
        and
        {zywork.joinWhereClause}
    </sql>

    <select id="getById" parameterType="long" resultType="{zywork.beanNameLowerCase}{zywork.doSuffix}">
        select
        <include refid="select_columns"/>
        from {zywork.tableName} where {zywork.primaryTable}.id = #{id} and
        {zywork.joinWhereClause}
    </select>

    <select id="listAll" resultType="{zywork.beanNameLowerCase}{zywork.doSuffix}">
        select
        <include refid="select_columns"/>
        from {zywork.tableName}
        where
        {zywork.joinWhereClause}
        order by {zywork.primaryTable}.update_time desc, {zywork.primaryTable}.create_time desc
    </select>

    <select id="listPageByCondition" resultType="{zywork.beanNameLowerCase}{zywork.doSuffix}">
        select
        <include refid="select_columns"/>
        from {zywork.tableName}
        <where>
            <include refid="query_where_clause"/>
        </where>
        order by
        <if test="query.sort != null and query.sort != ''">
            ${query.sort} ${query.order}
        </if>
        <if test="query.sort == null or query.sort == ''">
            {zywork.primaryTable}.update_time desc, {zywork.primaryTable}.create_time desc
        </if>
        limit #{query.beginIndex}, #{query.pageSize}
    </select>

    <select id="countByCondition" resultType="long">
        select count(*) from {zywork.tableName}
        <where>
            <include refid="query_where_clause"/>
        </where>
    </select>

</mapper>