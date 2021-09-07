package randommagics.render;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.Reference;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ShaderProgram {
    public final int programID; // индекс нашей шейдерной программы
    public static int currentShaderID = 0;

    public ShaderProgram(){
        programID = ARBShaderObjects.glCreateProgramObjectARB(); // генерируем индекс и задаем его
    }

    public ShaderProgram addFragment(String path){
        return add(path, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
    }

    public ShaderProgram addVertex(String path){
        return add(path, ARBVertexShader.GL_VERTEX_SHADER_ARB);
    }

    // метод дл€ добавлени€ в шейдерную программу какого-либо шейдера
    public ShaderProgram add(String path, int shaderType){
        int shaderID = ARBShaderObjects.glCreateShaderObjectARB(shaderType); // генерируем индекс нового шейдера
        ARBShaderObjects.glShaderSourceARB(shaderID, readFile(path)); // прив€зываем код шейдера к его индексу
        ARBShaderObjects.glCompileShaderARB(shaderID); // компилируем шейдер

        // провер€ем компил€цию на ошибки. ≈сли что-то пойдет не так, будет краш с ошибкой
        if (ARBShaderObjects.glGetObjectParameteriARB(shaderID,
                ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
            throw new RuntimeException("Shader compilation error!\n" +
                    ARBShaderObjects.glGetInfoLogARB(shaderID, ARBShaderObjects.
                            glGetObjectParameteriARB(shaderID, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB)));

        // если ошибок при компил€ции не было, прив€зываем шейдер к программе
        ARBShaderObjects.glAttachObjectARB(programID, shaderID);
        return this;
    }

    // метод дл€ компил€ции всей нашей программы
    public ShaderProgram compile(){
        ARBShaderObjects.glLinkProgramARB(programID);
        return this;
    }

    // включает шейдерную программу
    public void start(){
        ARBShaderObjects.glUseProgramObjectARB(programID);
        currentShaderID = this.programID;
    }

    // выключает шейдерную программу
    public void stop(){
        ARBShaderObjects.glUseProgramObjectARB(0);
        currentShaderID = 0;
    }

    // метод возвращает индекс юниформы из программы, по еЄ имени в шейдере
    public int getUniform(String name) {
        return ARBShaderObjects.glGetUniformLocationARB(programID, name);
    }

    // метод дл€ считывани€ файла из ресурса
    private String readFile(String path){
        try {
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("randommagics", path)).getInputStream(), "UTF-8"));
            String str;
            while ((str = reader.readLine()) != null)
                builder.append(str).append("\n");

            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}