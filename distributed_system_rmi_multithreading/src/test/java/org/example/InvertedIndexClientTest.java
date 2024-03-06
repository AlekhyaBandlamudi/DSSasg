package org.example;

import org.junit.jupiter.api.Test;

public class InvertedIndexClientTest {
	@Test
	public void testInvertedIndexClient() {
		// Adjust this path according to your environment
		String filePath = "C:\\Users\\abhis\\Desktop\\Assignment1_DSS\\distributed_system_rmi_multithreading\\distributed_system_rmi_multithreading\\src\\main\\resources\\sample_data.txt";
		InvertedIndexClient.main(new String[] { filePath });
	}
}
