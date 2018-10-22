<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="top.zywork.dao.{zywork.beanName}{zywork.daoSuffix}">

    <insert id="save" parameterType="{zywork.beanNameLowerCase}{zywork.doSuffix}">
        <selectKey resultType="java.lang.Long" order="AFTER" keyProperty="id">
    		SELECT LAST_INSERT_ID()
    	</selectKey>
        insert into {zywork.tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            {zywork.insertColumns}
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            {zywork.insertValues}
        </trim>
    </insert>

    <insert id="saveBatch" parameterType="java.util.List">
        <selectKey resultType="java.lang.Long" order="AFTER" keyProperty="id">
    		SELECT LAST_INSERT_ID()
    	</selectKey>
    	<foreach collection="list" item="item" separator=";">
            insert into {zywork.tableName}
            <trim prefix="(" suffix=")" suffixOverrides=",">
                {zywork.insertBatchColumns}
            </trim>
            <trim prefix="values (" suffix=")" suffixOverrides=",">
                {zywork.insertBatchValues}
            </trim>
        </foreach>
    </insert>

    <delete id="removeById" parameterType="long">
        delete from {zywork.tableName} where id = #{id}
    </delete>

    <delete id="removeByIds">
        delete from {zywork.tableName} where id in
        <foreach item="id" collection="array" separator="," open="(" close=")">
            #{id}
        </foreach>
    </delete>

    <update id="update" parameterType="{zywork.beanNameLowerCase}{zywork.doSuffix}">
        update {zywork.tableName}
        <set>
            {zywork.setClause}
        </set>
        where id = #{id}
    </update>

    <update id="updateBatch" parameterType="java.util.List">
    	<foreach collection="list" item="item" separator=";">
            update {zywork.tableName}
            <set>
                {zywork.setBatchClause}
            </set>
            where id = #{item.id}
        </foreach>
    </update>

    <sql id="select_columns">
        {zywork.selectColumns}
    </sql>

    <sql id="query_where_clause">
        {zywork.queryWhereClause}
    </sql>

    <select id="getById" parameterType="long" resultType="{zywork.beanNameLowerCase}{zywork.doSuffix}">
        select
        <include refid="select_columns"/>
        from {zywork.tableName} where id = #{id}
    </select>

    <select id="listAll" resultType="{zywork.beanNameLowerCase}{zywork.doSuffix}">
        select
        <include refid="select_columns"/>
        from {zywork.tableName}
        order by update_time desc, create_time desc
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
            update_time desc, create_time desc
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