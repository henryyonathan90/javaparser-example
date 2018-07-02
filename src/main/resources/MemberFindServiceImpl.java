package henry.jonathan.javaparser.example;

import com.gdn.x.member.dao.api.MemberRepository;
import com.gdn.x.member.dao.api.solr.MemberSolrRepository;
import com.gdn.x.member.model.constant.MemberSolrFieldNames;
import com.gdn.x.member.model.entity.Member;
import com.gdn.x.member.model.solr.MemberSolr;
import com.gdn.x.member.service.api.MemberFindService;
import com.gdn.x.member.service.api.SolrReindexService;
import com.gdn.x.member.service.api.SystemParameterService;
import com.gdn.x.member.vo.BasicMemberQueryVo;
import com.gdn.x.member.vo.MemberPage;
import com.gdn.x.member.vo.QueryVariable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.google.common.base.Preconditions.checkArgument;

@Service
public class MemberFindServiceImpl implements MemberFindService {


  private static final String MEMBER_ID_MUST_NOT_BE_BLANK = "memberId must not be blank";

  private static final String BASIC_QUERY_MUST_NOT_BE_NULL = "basic query must not be null";

  private static final String STORE_ID_MUST_NOT_BE_BLANK = "storeId must not be blank";

  @Autowired
  private MemberSolrRepository memberSolrRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  SolrReindexService solrReindexService;

  @Autowired
  ExecutorService executorService;

  @Autowired
  SystemParameterService systemParameterService;

  private List<String> construcMemberIds(List<MemberSolr> memberSolrs) {
    List<String> memberIds = new ArrayList<String>();
    for (MemberSolr memberSolr : memberSolrs) {
      memberIds.add(memberSolr.getMemberId());
    }
    return memberIds;
  }

  @Override
  public MemberPage findByBasicCustomerDataLike(String storeId, BasicMemberQueryVo query,
      Pageable pageable) {
    checkArgument(StringUtils.isNotBlank(storeId), MemberFindServiceImpl.STORE_ID_MUST_NOT_BE_BLANK);
    checkArgument(query != null, MemberFindServiceImpl.BASIC_QUERY_MUST_NOT_BE_NULL);
    List<QueryVariable> queries = new ArrayList<QueryVariable>();
    if (StringUtils.isNotBlank(query.getMemberId())) {
      queries.add(new QueryVariable(MemberSolrFieldNames.MEMBER_ID, query.getMemberId()));
    }
    if (StringUtils.isNotBlank(query.getEmail())) {
      queries.add(new QueryVariable(MemberSolrFieldNames.EMAIL, query.getEmail()));
    }
    if (StringUtils.isNotBlank(query.getFirstName())) {
      queries.add(new QueryVariable(MemberSolrFieldNames.FIRST_NAME, query.getFirstName()));
    }
    if (StringUtils.isNotBlank(query.getLastName())) {
      queries.add(new QueryVariable(MemberSolrFieldNames.LAST_NAME, query.getLastName()));
    }
    if (StringUtils.isNotBlank(query.getHandphone())) {
      queries.add(new QueryVariable(MemberSolrFieldNames.MOBILE_PHONE, query.getHandphone()));
    }
    if (!queries.isEmpty()) {
      queries.add(new QueryVariable(MemberSolrFieldNames.STORE_ID, storeId));
    }
    Page<MemberSolr> memberSolrs = this.memberSolrRepository.findAnd(queries, pageable);
    if (memberSolrs != null && memberSolrs.getContent() != null) {
      List<String> memberIds = this.construcMemberIds(memberSolrs.getContent());
      return new MemberPage(this.memberRepository.findByStoreIdAndMemberIdIn(storeId, memberIds),
          memberSolrs.getTotalElements());
    }
    else {
      return new MemberPage(null, 0L);
    }
  }

  @Override public void reindexMemberSolr(String storeId, List<String> memberIds) {
    checkArgument(StringUtils.isNotBlank(storeId),
        MemberFindServiceImpl.STORE_ID_MUST_NOT_BE_BLANK);
    checkArgument(memberIds != null, MemberFindServiceImpl.MEMBER_ID_MUST_NOT_BE_BLANK);
    List<Member> members = this.memberRepository.findByStoreIdAndMemberIdIn(storeId, memberIds);
    for (Member member : members) {
      this.memberRepository.updateVersion(storeId, member.getMemberId(), member.getVersion());
    }
  }

}
