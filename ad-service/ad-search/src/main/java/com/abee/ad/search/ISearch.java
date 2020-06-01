package com.abee.ad.search;

import com.abee.ad.search.vo.SearchRequest;
import com.abee.ad.search.vo.SearchResponse;

/**
 * @author xincong yao
 */
public interface ISearch {

    SearchResponse fetchAds(SearchRequest request);
}
