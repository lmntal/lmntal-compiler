package runtime.exception;

@SuppressWarnings("serial")
public class GuardNotFoundException extends Exception
{
	public GuardNotFoundException()
	{
	}

	public GuardNotFoundException(String message)
	{
		super(message);
	}

	public GuardNotFoundException(Throwable cause)
	{
		super(cause);
	}

	public GuardNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
