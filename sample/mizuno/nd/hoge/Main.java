public class Main {
	public static void loadUserDefinedSystemRuleset() {
		loadSystemRulesetFromModule("java");
		loadSystemRulesetFromModule("nd");
	}
	private static void loadSystemRulesetFromModule(String moduleName) {
		try {
			Class c = Class.forName("translated.Module_" + moduleName);
			java.lang.reflect.Method method = c.getMethod("loadUserDefinedSystemRuleset", null);
			method.invoke(null, null);
		} catch (ClassNotFoundException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalAccessException e) {
		} catch (java.lang.reflect.InvocationTargetException e) {
		}
	}
	public static void main(String[] args) {
		runtime.FrontEnd.processOptions(args);
		loadUserDefinedSystemRuleset();
		runtime.FrontEnd.run(translated.Ruleset602.getInstance());
	}
}
