package translated;
import translated.module_integer.*;
import runtime.SystemRulesets;
import runtime.Ruleset;
public class Module_integer{
	private static Ruleset[] rulesets = {Ruleset609.getInstance()};
	public static Ruleset[] getRulesets() {
		return rulesets;
	}
	public static void loadUserDefinedSystemRuleset() {
		runtime.Inline.inlineSet.put("public/integer.lmn", new runtime.InlineUnit("public/integer.lmn"));
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
}
