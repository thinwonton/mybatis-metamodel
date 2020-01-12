package com.github.thinwonton.mybatis.metamodelgen.test.tkmapper.entity;

import com.github.thinwonton.mybatis.metamodel.core.annotation.GenMetaModel;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.*;
import java.io.Serializable;

@GenMetaModel
@Table(name = "music")
public class Music extends Entity implements Serializable {
    private static final long serialVersionUID = 767652858834706096L;

    private transient Log log = LogFactory.getLog(getClass());

    @Transient
    private byte[] content;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "authorName")
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private String authorName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
