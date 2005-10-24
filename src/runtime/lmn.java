package runtime;

/**
 * --interpret --use-source-library -x dump 1 をつけたフロントエンド
 *
 */
public class lmn extends FrontEnd {
	static{
		Env.fInterpret = Env.fUseSourceLibrary = true;
		Env.extendedOption.put("dump", "1");
	}
}
