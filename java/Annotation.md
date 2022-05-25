
JDK1.5 버전 이상에서 사용 가능하다.

## 자바에서의 Annotation

|Annotation|설명|
|---------|----|
|@Override|선언한 메서드가 오버라이드 되었다는 의미|
|@Deprecated|해당 메서드 사용을 권장하지 않음을 의미|
|@SuppressWarnings|선언한 곳의 컴파일 경고를 무시하도록 설정|
|@SafeVarargs|제너릭 같은 가변인자의 매개변수를 사용할 때 경고를 무시|
|@FunctionlInterface|함수형 인터페이스를 지정하는 어노테이션|
|@Retention|자바 컴파일러가 어노테이션을 다루는 방법을 기술하며|
||특정 시점까지 영향을 미치는지를 결정|
|RetentionPolicy.SOURCE|컴파일 전까지만 유효(컴파일 이후에 사라짐)|
|RetentionPolicy.CLASS|컴파일러가 클래스를 참조할때까지만 유효|
|RetentionPolicy.RUNTIME|컴파일 이후에도 JVM에 의해 계속 참조 가능(리플렉션 사용)|
|-----|-----|
|@Documented|Javadoc에 해당 어노테이션을 포함|
|@Target|어노테이션이 적용할 위치를 선택|
|ElementType.PACKAGE|패키지 선언|
|ElementType.TYPE|타입 선언|
|ElementType.ANNOTATION_TYPE|어노테이션 타입 선언|
|ElementType.CONSTRUCTOR|생성자 선언|
|ElementType.FIELD|멤버변수 선언|
|ElementType.LOCAL_VARIABLE|지역변수 선언|
|ElementType.METHOD|메서드 선언|
|ElementType.PARAMETER|파라미터 선언|
|ElementType.TYPE_PARAMETER|파라미터 타입 선언|
|ElementType.TYPE_USE|타입 선언|
|-----|-----|
|@Inherited|어노테이션의 상속을 가능하게 함|
|@Repeatable|연속적으로 어노테이션을 선언할 수 있다|
