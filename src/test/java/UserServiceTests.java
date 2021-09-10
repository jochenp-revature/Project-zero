

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.exceptions.RegisterUserFailedException;
import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserDao;
import com.revature.services.UserService;

public class UserServiceTests {
	
	// declare the class to be tested
	private UserService userv;
	
	// declare the DEPENDENCIES of the userservice
	// We will be using Mockito to "mock" the dao layer, that the UserService is dependent on
	private UserDao mockdao;
	
	private User dummyUser;
	
	@Before // before every test is run
	public void setup() {
		
		userv = new UserService();
		
		// mock the dependency that's privded to the UserService
		mockdao = mock(UserDao.class);	
		
		// every userService has a DAO that it relies on...
		userv.udao = mockdao;
		
		
		// this is a stub
		dummyUser = new User();
		dummyUser.setAccounts(new ArrayList<Account>());
		dummyUser.setId(0);
	}
	
	@After // after each unit test runs
	public void teardown() {
		
		userv = null;
		dummyUser = null;
		mockdao = null;
		
	}
	
	
	// whenever we insert a user, it reutrns a valid primary key
	// if it were NOT returning the right key, we'd have some major problems
	@Test
	public void testRegisterUser_returnsNewPk() {
		
		dummyUser = new User(0, "spongebob", "pineapple", Role.Admin, new ArrayList<>());
			
		// Generate a hard coded random primary number that twill be returned by the mockdao's insert method
		Random r = new Random();
		int fakePk = r.nextInt(100);
		
		// Here we are intercepting the UserService's call to the DAO layer
		when(mockdao.insert(dummyUser)).thenReturn(fakePk); // this user should never be inserted into our database
		
		// The above line is a simulated data returned from the database 
		
		// Here we're testing does the register method properly call our dao layer?
		User registeredUser = userv.register(dummyUser);
		
		assertEquals(registeredUser.getId(), fakePk);
	}
	
	
	@Test(expected=RegisterUserFailedException.class)
	public void testRegisterUser_isGreaterThanZero_throwsException() {
	
		// here we try registering a User whoseid is > 0 
		
		List<Account> accounts = new ArrayList<Account>();
		Account a = new Account();
		a.setBalance(7000);

		accounts.add(a);
		
		dummyUser = new User(23, "Bob", "pass", Role.Customer, accounts);
		
		userv.register(dummyUser); // this will throw an exception because the
		// user that we're trying to register has an id > 0;
	}

}