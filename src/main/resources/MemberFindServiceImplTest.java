package com.gdn.x.member.service.impl;

import com.gdn.common.web.param.PageableHelper;
import com.gdn.x.member.dao.api.MemberRepository;
import com.gdn.x.member.dao.api.solr.MemberSolrRepository;
import com.gdn.x.member.model.constant.MemberSolrFieldNames;
import com.gdn.x.member.model.entity.Member;
import com.gdn.x.member.model.solr.MemberSolr;
import com.gdn.x.member.service.api.SystemParameterService;
import com.gdn.x.member.vo.BasicMemberQueryVo;
import com.gdn.x.member.vo.QueryVariable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class MemberFindServiceImplTest {

  private static final long VERSION = 5000L;

  private static final String MEMBER_ID2 = "memberId2";

  private static final String MEMBER_ID1 = "memberId1";

  private static final String HAND_PHONE = "handPhone";

  private static final String EMAIL = "email";

  private static final String LAST_NAME = "lastName";

  private static final String FIRST_NAME = "firstName";

  private static final String STORE_ID = "storeId";

  @InjectMocks
  private MemberFindServiceImpl memberFindService;

  @Mock
  private MemberSolrRepository memberSolrRepository;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private Page<MemberSolr> memberSolrPage;

  @Mock
  private SystemParameterService systemParameterService;

  @Mock
  private ExecutorService executorService;

  private List<MemberSolr> memberSolrs;

  private static final Long TOTAL_RECORDS = 2L;

  private static final Pageable PAGE = PageableHelper.generatePageable(1, 1);

  private static final List<String> MEMBER_IDS = Arrays.asList(
      com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_ID1, com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_ID2);

  @Test
  public void findByBasicCustomerDataLikeSuccess() {

    List<QueryVariable> queries =
        Arrays.asList(new QueryVariable(MemberSolrFieldNames.MEMBER_ID,
            com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_ID1), new QueryVariable(MemberSolrFieldNames.EMAIL,
            com.gdn.x.member.service.impl.MemberFindServiceImplTest.EMAIL), new QueryVariable(MemberSolrFieldNames.FIRST_NAME,
            com.gdn.x.member.service.impl.MemberFindServiceImplTest.FIRST_NAME), new QueryVariable(
            MemberSolrFieldNames.LAST_NAME, com.gdn.x.member.service.impl.MemberFindServiceImplTest.LAST_NAME),
            new QueryVariable(MemberSolrFieldNames.MOBILE_PHONE,
                com.gdn.x.member.service.impl.MemberFindServiceImplTest.HAND_PHONE), new QueryVariable(
                MemberSolrFieldNames.STORE_ID, com.gdn.x.member.service.impl.MemberFindServiceImplTest.STORE_ID));
    when(this.memberSolrRepository.findAnd(queries, com.gdn.x.member.service.impl.MemberFindServiceImplTest.PAGE)).thenReturn(
        this.memberSolrPage);

    this.memberFindService.findByBasicCustomerDataLike(com.gdn.x.member.service.impl.MemberFindServiceImplTest.STORE_ID,
        new BasicMemberQueryVo(com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_ID1,
            com.gdn.x.member.service.impl.MemberFindServiceImplTest.FIRST_NAME, com.gdn.x.member.service.impl.MemberFindServiceImplTest.LAST_NAME,
            com.gdn.x.member.service.impl.MemberFindServiceImplTest.EMAIL, com.gdn.x.member.service.impl.MemberFindServiceImplTest.HAND_PHONE),
        com.gdn.x.member.service.impl.MemberFindServiceImplTest.PAGE);
    verify(this.memberSolrRepository).findAnd(queries, com.gdn.x.member.service.impl.MemberFindServiceImplTest.PAGE);
    verify(this.memberRepository).findByStoreIdAndMemberIdIn(com.gdn.x.member.service.impl.MemberFindServiceImplTest.STORE_ID,
        com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_IDS);
    verify(this.memberSolrPage, times(2)).getContent();
    verify(this.memberSolrPage).getTotalElements();
  }

  @Test
  public void findByBasicCustomerDataLikeNoResponse() {
    List<QueryVariable> queries =
        Arrays.asList(new QueryVariable(MemberSolrFieldNames.MEMBER_ID,
            com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_ID1), new QueryVariable(MemberSolrFieldNames.EMAIL,
            com.gdn.x.member.service.impl.MemberFindServiceImplTest.EMAIL), new QueryVariable(MemberSolrFieldNames.FIRST_NAME,
            com.gdn.x.member.service.impl.MemberFindServiceImplTest.FIRST_NAME), new QueryVariable(
            MemberSolrFieldNames.LAST_NAME, com.gdn.x.member.service.impl.MemberFindServiceImplTest.LAST_NAME),
            new QueryVariable(MemberSolrFieldNames.MOBILE_PHONE,
                com.gdn.x.member.service.impl.MemberFindServiceImplTest.HAND_PHONE), new QueryVariable(
                MemberSolrFieldNames.STORE_ID, com.gdn.x.member.service.impl.MemberFindServiceImplTest.STORE_ID));
    when(this.memberSolrRepository.findAnd(queries, com.gdn.x.member.service.impl.MemberFindServiceImplTest.PAGE)).thenReturn(null);

    this.memberFindService.findByBasicCustomerDataLike(com.gdn.x.member.service.impl.MemberFindServiceImplTest.STORE_ID,
        new BasicMemberQueryVo(com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_ID1,
            com.gdn.x.member.service.impl.MemberFindServiceImplTest.FIRST_NAME, com.gdn.x.member.service.impl.MemberFindServiceImplTest.LAST_NAME,
            com.gdn.x.member.service.impl.MemberFindServiceImplTest.EMAIL, com.gdn.x.member.service.impl.MemberFindServiceImplTest.HAND_PHONE),
        com.gdn.x.member.service.impl.MemberFindServiceImplTest.PAGE);
    verify(this.memberSolrRepository).findAnd(queries, com.gdn.x.member.service.impl.MemberFindServiceImplTest.PAGE);
  }

  @Test
  public void findByBasicCustomerDataLike_NoResponseTest() {
    List<QueryVariable> queries = Arrays.asList(
        new QueryVariable(MemberSolrFieldNames.MEMBER_ID, com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_ID1),
        new QueryVariable(MemberSolrFieldNames.EMAIL, com.gdn.x.member.service.impl.MemberFindServiceImplTest.EMAIL),
        new QueryVariable(MemberSolrFieldNames.FIRST_NAME, com.gdn.x.member.service.impl.MemberFindServiceImplTest.FIRST_NAME),
        new QueryVariable(MemberSolrFieldNames.LAST_NAME, com.gdn.x.member.service.impl.MemberFindServiceImplTest.LAST_NAME),
        new QueryVariable(MemberSolrFieldNames.MOBILE_PHONE, com.gdn.x.member.service.impl.MemberFindServiceImplTest.HAND_PHONE),
        new QueryVariable(MemberSolrFieldNames.STORE_ID, com.gdn.x.member.service.impl.MemberFindServiceImplTest.STORE_ID));
    when(this.memberSolrRepository.findAnd(queries, com.gdn.x.member.service.impl.MemberFindServiceImplTest.PAGE))
        .thenReturn(memberSolrPage);

    when(this.memberSolrPage.getContent()).thenReturn(null);
    
    this.memberFindService.findByBasicCustomerDataLike(com.gdn.x.member.service.impl.MemberFindServiceImplTest.STORE_ID,
        new BasicMemberQueryVo(com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_ID1,
            com.gdn.x.member.service.impl.MemberFindServiceImplTest.FIRST_NAME, com.gdn.x.member.service.impl.MemberFindServiceImplTest.LAST_NAME,
            com.gdn.x.member.service.impl.MemberFindServiceImplTest.EMAIL, com.gdn.x.member.service.impl.MemberFindServiceImplTest.HAND_PHONE),
        com.gdn.x.member.service.impl.MemberFindServiceImplTest.PAGE);
    verify(this.memberSolrRepository).findAnd(queries, com.gdn.x.member.service.impl.MemberFindServiceImplTest.PAGE);
    verify(this.memberSolrPage).getContent();
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void findByBasicCustomerDataLikeWithBlankStoreId() {
    this.memberFindService.findByBasicCustomerDataLike(null, new BasicMemberQueryVo(),
        com.gdn.x.member.service.impl.MemberFindServiceImplTest.PAGE);
  }

  @Test
  public void findByBasicCustomerDataLikeWithEmptyBasicQuery() {
    List<QueryVariable> queries = new ArrayList<QueryVariable>();
    when(this.memberSolrRepository.findAnd(queries, com.gdn.x.member.service.impl.MemberFindServiceImplTest.PAGE)).thenReturn(
        this.memberSolrPage);

    this.memberFindService.findByBasicCustomerDataLike(com.gdn.x.member.service.impl.MemberFindServiceImplTest.STORE_ID,
        new BasicMemberQueryVo(), com.gdn.x.member.service.impl.MemberFindServiceImplTest.PAGE);
    verify(this.memberSolrRepository).findAnd(queries, com.gdn.x.member.service.impl.MemberFindServiceImplTest.PAGE);
    verify(this.memberRepository).findByStoreIdAndMemberIdIn(com.gdn.x.member.service.impl.MemberFindServiceImplTest.STORE_ID,
        com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_IDS);
    verify(this.memberSolrPage, times(2)).getContent();
    verify(this.memberSolrPage).getTotalElements();
  }

  @Test(expected = IllegalArgumentException.class)
  public void findByBasicCustomerDataLikeWithNullBasicQuery() {
    this.memberFindService.findByBasicCustomerDataLike(com.gdn.x.member.service.impl.MemberFindServiceImplTest.STORE_ID, null,
        com.gdn.x.member.service.impl.MemberFindServiceImplTest.PAGE);
  }

  @Before
  public void init() {
    initMocks(this);
    this.memberSolrs = new ArrayList<MemberSolr>();
    MemberSolr memberSolr1 = new MemberSolr();
    memberSolr1.setMemberId(com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_ID1);
    MemberSolr memberSolr2 = new MemberSolr();
    memberSolr2.setMemberId(com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_ID2);
    this.memberSolrs.add(memberSolr1);
    this.memberSolrs.add(memberSolr2);

    when(this.memberSolrPage.getContent()).thenReturn(this.memberSolrs);
    when(this.memberSolrPage.getTotalElements())
        .thenReturn(com.gdn.x.member.service.impl.MemberFindServiceImplTest.TOTAL_RECORDS);
  }

  @Test(expected = IllegalArgumentException.class)
  public void reindexMemberSolrBlankStoreId() {
    this.memberFindService.reindexMemberSolr(null, com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_IDS);
  }

  @Test(expected = IllegalArgumentException.class)
  public void reindexMemberSolrNullMemberIds() {
    this.memberFindService.reindexMemberSolr(com.gdn.x.member.service.impl.MemberFindServiceImplTest.STORE_ID, null);
  }

  @Test
  public void reindexMemberSolrSuccess() {
    List<Member> members = getMemberList();
    when(
        this.memberRepository.findByStoreIdAndMemberIdIn(com.gdn.x.member.service.impl.MemberFindServiceImplTest.STORE_ID,
            com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_IDS)).thenReturn(members);
    this.memberFindService.reindexMemberSolr(com.gdn.x.member.service.impl.MemberFindServiceImplTest.STORE_ID,
        com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_IDS);
    verify(this.memberRepository).findByStoreIdAndMemberIdIn(com.gdn.x.member.service.impl.MemberFindServiceImplTest.STORE_ID,
        com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_IDS);
    verify(this.memberRepository).updateVersion(com.gdn.x.member.service.impl.MemberFindServiceImplTest.STORE_ID,
        com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_ID1, com.gdn.x.member.service.impl.MemberFindServiceImplTest.VERSION);
    verify(this.memberRepository).updateVersion(com.gdn.x.member.service.impl.MemberFindServiceImplTest.STORE_ID,
        com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_ID2, com.gdn.x.member.service.impl.MemberFindServiceImplTest.VERSION);
  }
  private List<Member> getMemberList(){
    List<Member> members = new ArrayList<Member>();
    Member member1 = new Member();
    member1.setVersion(com.gdn.x.member.service.impl.MemberFindServiceImplTest.VERSION);
    member1.setMemberId(com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_ID1);
    Member member2 = new Member();
    member2.setVersion(com.gdn.x.member.service.impl.MemberFindServiceImplTest.VERSION);
    member2.setMemberId(com.gdn.x.member.service.impl.MemberFindServiceImplTest.MEMBER_ID2);
    members.add(member1);
    members.add(member2);
    return members;
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(this.memberSolrRepository);
    verifyNoMoreInteractions(this.memberRepository);
    verifyNoMoreInteractions(this.memberSolrPage);
  }
}
