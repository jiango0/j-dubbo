package com.jzc.spring.dubbo.module.release.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SearchDto
 * @Description: SearchDto
 * @author wangsenyong
 * @date 2017-11-22 14:15:19
 *
 */
public class SearchDto implements Serializable {

    private Integer currentPage = Integer.valueOf(1);

    private Integer pageSize = Integer.valueOf(10);

    /**
     * 关键字匹配
     */
    private String keyWord;

    /**
     *必须匹配的字段
     */
    private Map<String,Object> mustMatchMap;

    /**
     * 关键字可能匹配的字段
     */
    private List<String> shouldMatchValues;

    /**
     * 排序
     */
    private List<String> sorts;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public List<String> getSorts() {
        return sorts;
    }

    public void setSorts(List<String> sorts) {
        this.sorts = sorts;
    }

    public Map<String, Object> getMustMatchMap() {
        return mustMatchMap;
    }

    public void setMustMatchMap(Map<String, Object> mustMatchMap) {
        this.mustMatchMap = mustMatchMap;
    }

    public List<String> getShouldMatchValues() {
        return shouldMatchValues;
    }

    public void setShouldMatchValues(List<String> shouldMatchValues) {
        this.shouldMatchValues = shouldMatchValues;
    }

}
