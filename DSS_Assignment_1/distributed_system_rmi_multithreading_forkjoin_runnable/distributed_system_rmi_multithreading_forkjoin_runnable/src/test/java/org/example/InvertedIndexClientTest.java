// InvertedIndexClientTest.java
package org.example;


import org.junit.jupiter.api.Test;

public class InvertedIndexClientTest {
	@Test
	public void testInvertedIndexClient() {
		// Adjust this path according to your environment
		String filePath = "C:\\Users\\alekh\\Downloads\\distributed_system_rmi_multithreading_forkjoin_runnable\\distributed_system_rmi_multithreading_forkjoin_runnable\\src\\main\\resources\\sample_data.txt";
		InvertedIndexClient.main(new String[] { filePath });
	}
}
