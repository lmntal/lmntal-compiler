package translated;
import translated.module_string.*;
import runtime.SystemRulesets;
import runtime.Ruleset;
public class Module_string{
	private static Ruleset[] rulesets = {Ruleset617.getInstance()};
	public static Ruleset[] getRulesets() {
		return rulesets;
	}
	public static void loadUserDefinedSystemRuleset() {
		runtime.Inline.inlineSet.put("public/string.lmn", new runtime.InlineUnit("public/string.lmn"));
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
