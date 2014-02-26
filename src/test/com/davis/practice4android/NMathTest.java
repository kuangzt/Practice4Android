package test.com.davis.practice4android;

import android.test.AndroidTestCase;

import com.davis.practice4android.jni.NMath;

public class NMathTest extends AndroidTestCase {

	NMath mNMath;
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		mNMath = new NMath();
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		mNMath = null;
	}

	public void testAdd(){//无参数，无返回值，test开头的方法，run as-> Android JUnit Test时会自动调用
		mNMath.add(3, 6);
	}
	
	public  void testsub(){//无参数，无返回值，test开头的方法，run as-> Android JUnit Test时会自动调用
		mNMath.sub(3, 1);
	}
	
}
