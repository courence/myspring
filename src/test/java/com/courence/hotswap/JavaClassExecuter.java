package com.courence.hotswap;

import java.lang.reflect.Method;

public class JavaClassExecuter {
	public static String execute(byte[] classByte){
		HackSystem.clearBuffer();
		ClassModifier cm = new ClassModifier(classByte);
		byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System", "com.courence.hotswap/HackSystem");
		HotSwapClassLoader loader = new HotSwapClassLoader();
		Class<?> clazz = loader.loadByte(modiBytes);
		try{
			Method method = clazz.getMethod("main", new Class[]{String[].class});
		}
		catch(Throwable e){
			e.printStackTrace(HackSystem.out);
		}
		return HackSystem.getBufferString();
	}
}
