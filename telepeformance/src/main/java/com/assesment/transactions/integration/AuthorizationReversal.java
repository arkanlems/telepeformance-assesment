package com.assesment.transactions.integration;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReversalApi;
import Data.Configuration;
import Invokers.ApiClient;
import Model.AuthReversalRequest;
import Model.PtsV2PaymentsPost201Response;
import Model.PtsV2PaymentsReversalsPost201Response;
import Model.Ptsv2paymentsidreversalsClientReferenceInformation;
import Model.Ptsv2paymentsidreversalsReversalInformation;
import Model.Ptsv2paymentsidreversalsReversalInformationAmountDetails;


public class AuthorizationReversal {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run();
	}

	public static PtsV2PaymentsReversalsPost201Response run() {
		PtsV2PaymentsPost201Response paymentResponse = SimpleAuthorizationInternet.run();
		String id = paymentResponse.getId();
	
		AuthReversalRequest requestObj = new AuthReversalRequest();

		Ptsv2paymentsidreversalsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsidreversalsClientReferenceInformation();
		clientReferenceInformation.code("TC50171_3");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsidreversalsReversalInformation reversalInformation = new Ptsv2paymentsidreversalsReversalInformation();
		Ptsv2paymentsidreversalsReversalInformationAmountDetails reversalInformationAmountDetails = new Ptsv2paymentsidreversalsReversalInformationAmountDetails();
		reversalInformationAmountDetails.totalAmount("102.21");
		reversalInformation.amountDetails(reversalInformationAmountDetails);

		reversalInformation.reason("testing");
		requestObj.reversalInformation(reversalInformation);

		PtsV2PaymentsReversalsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			ReversalApi apiInstance = new ReversalApi(apiClient);
			result = apiInstance.authReversal(id, requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return result;
	}
}
