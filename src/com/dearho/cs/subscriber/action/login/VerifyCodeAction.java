/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file VerifyCodeAction.java creation date:[2015-5-29 下午02:27:17] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.subscriber.action.login;

import com.dearho.cs.sys.action.AbstractAction;


/**
 * @Author liusong
 * @Description:
 * @Version 1.0,2015-5-29
 *
 */
public class VerifyCodeAction extends AbstractAction {


	private static final long serialVersionUID = -465099877613477131L;

	@Override
	public String process() {
	/*	try {
		ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
		cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
		cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));

		FileOutputStream fos = new FileOutputStream("patcha_demo.png");
		
		String patchca=EncoderHelper.getChallangeAndWriteImage(cs, "png", fos);
		getSession().setAttribute("PATCHCA", patchca);
		
		fos.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		*/
		return SUCCESS;
	}

	
	


}

