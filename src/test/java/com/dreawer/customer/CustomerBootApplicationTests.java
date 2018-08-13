package com.dreawer.customer;

import com.dreawer.customer.form.AddMemberRankForm;
import com.dreawer.customer.lang.MemberRankExpiration;
import com.dreawer.customer.lang.MemberRankStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class CustomerBootApplicationTests {

	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;


	@Before
	public void setUp() throws Exception {
		//       mvc = MockMvcBuilders.standaloneSetup(new TestController()).build();
		mvc = MockMvcBuilders.webAppContextSetup(context).build();//建议使用这种
	}

	@Test
	public void contextLoads() {


	}


	@Test
	public void testMember() throws Exception {
		AddMemberRankForm form = new AddMemberRankForm();
		form.setStoreId("34d803197cd94a90b312cdd74684ac9f");
		form.setName("1111");
		form.setGrowthValue(100);
		form.setFreeShipping(true);
		form.setDiscount(true);
		form.setDiscountAmount(new BigDecimal(9));
		form.setExpiration(MemberRankExpiration.UNLIMITED);
		form.setPeriod(12);
		form.setExpireDeduction(11);
		form.setStatus(MemberRankStatus.ENABLE);
		form.setUserId(UUID.randomUUID().toString().replace("-",""));
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(form);
		mvc.perform(MockMvcRequestBuilders.post("/member/rank/add")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(requestJson)
				.header("userId",UUID.randomUUID().toString().replace("-",""))
				.sessionAttr("111", "111"))
				.andDo(print());
	}

//	@Test
//	public void testMemberAdd() throws Exception {
//		RegisterMemberForm form = new RegisterMemberForm();
//		form.setStoreId("34d803197cd94a90b312cdd74684ac9f");
//		form.setNickName("222222");
//		form.setPhoneNumber("13349953687");
//		form.setUserName("11111");
//		form.setSex(1);
//		form.setMugshot("11111111111");
//		form.setBirthday(String.valueOf(new Date().getTime()));
//		ObjectMapper mapper = new ObjectMapper();
//		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
//		String requestJson = ow.writeValueAsString(form);
//		mvc.perform(MockMvcRequestBuilders.post("/member/register")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(requestJson)
//				.header("userId",UUID.randomUUID().toString().replace("-",""))
//				.sessionAttr("111", "111"))
//				.andDo(print());
//		String response = HttpClientUtil.doPostJSON("http://localhost:9092/member/register", requestJson);
//		System.out.println(response);
//	}
	
	public static void main(String[] args) {
		
		
	}

}
