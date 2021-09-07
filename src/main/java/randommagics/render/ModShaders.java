package randommagics.render;

public class ModShaders {
    public static ShaderProgram glitch;
    public static ShaderProgram grey;

    // этот метод мы вызываем при инициализации мода, что бы зарегестрировать все наши шейдеры
    public static void register(){
        // путь к файлам шейдера мы указываем относительно нашей папки в ассетах
        glitch = new ShaderProgram()
                .addFragment("shaders/glitch.frag") // добавл€ем фрагментный
                .addVertex("shaders/glitch.vert") // добавл€ем вертексный
                .compile(); // после добавлени€ надо вызывать об€зательно компил€цию
        
        grey = new ShaderProgram().addFragment("shaders/grey.frag").addVertex("shaders/grey.vert").compile();
    }
}
