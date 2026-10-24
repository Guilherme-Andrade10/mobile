package com.example.protege_dds

// O Context permite que o banco tenha acesso ao aplicativo
import android.content.Context

// Permite colocar valores dentro do banco
import android.content.ContentValues

// Representa o banco de dados SQLite
import android.database.sqlite.SQLiteDatabase

// Facilita a criação e atualização do banco
import android.database.sqlite.SQLiteOpenHelper


// Classe responsável pelo banco de dados
class BancoDados(context: Context) :

// Cria o banco com nome, versão e contexto
    SQLiteOpenHelper(context, "protege_dds.db", null, 1) {


    // Cria as tabelas quando o banco é criado pela primeira vez
    override fun onCreate(db: SQLiteDatabase) {


        // Cria a tabela de usuários
        db.execSQL("""
            CREATE TABLE usuarios (

                -- Identificador único do usuário
                id INTEGER PRIMARY KEY AUTOINCREMENT,

                -- Nome do usuário
                nome TEXT NOT NULL,

                -- CPF do usuário
                cpf TEXT,

                -- Senha do usuário
                senha TEXT NOT NULL,

                -- Define o tipo de usuário
                tipo TEXT NOT NULL
            )
        """)


        // Cria a tabela de DDS
        db.execSQL("""
            CREATE TABLE dds (

                -- Identificador único do DDS
                id INTEGER PRIMARY KEY AUTOINCREMENT,

                -- Título do DDS
                titulo TEXT NOT NULL,

                -- Texto do DDS
                texto TEXT NOT NULL,

                -- Arquivo anexado ao DDS
                arquivo TEXT,

                -- Data e hora da publicação
                data_publicacao TEXT NOT NULL,

                -- Identifica o TST que publicou
                id_tst INTEGER
            )
        """)


        // Cria a tabela de visualizações
        db.execSQL("""
            CREATE TABLE visualizacoes (

                -- Identificador da visualização
                id INTEGER PRIMARY KEY AUTOINCREMENT,

                -- Identifica o colaborador
                id_usuario INTEGER NOT NULL,

                -- Identifica o DDS visualizado
                id_dds INTEGER NOT NULL,

                -- Data e hora da visualização
                data_visualizacao TEXT NOT NULL
            )
        """)


        // Cria a tabela do emociômetro
        db.execSQL("""
            CREATE TABLE emociometro (

                -- Identificador da resposta
                id INTEGER PRIMARY KEY AUTOINCREMENT,

                -- Identifica o colaborador
                id_usuario INTEGER NOT NULL,

                -- Identifica o DDS relacionado
                id_dds INTEGER NOT NULL,

                -- Guarda o estado emocional
                estado TEXT NOT NULL,

                -- Data e hora da resposta
                data_registro TEXT NOT NULL
            )
        """)
    }


    // Atualiza o banco quando sua versão é alterada
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

        // Apaga a tabela do emociômetro
        db.execSQL("DROP TABLE IF EXISTS emociometro")

        // Apaga a tabela de visualizações
        db.execSQL("DROP TABLE IF EXISTS visualizacoes")

        // Apaga a tabela de DDS
        db.execSQL("DROP TABLE IF EXISTS dds")

        // Apaga a tabela de usuários
        db.execSQL("DROP TABLE IF EXISTS usuarios")

        // Cria novamente todas as tabelas
        onCreate(db)
    }


    // Cadastra um novo usuário
    fun cadastrarUsuario(
        nome: String,
        cpf: String,
        senha: String,
        tipo: String
    ): Long {

        // Abre o banco para inserir dados
        val db = writableDatabase

        // Cria um conjunto de valores para inserir
        val valores = ContentValues()

        // Adiciona o nome
        valores.put("nome", nome)

        // Adiciona o CPF
        valores.put("cpf", cpf)

        // Adiciona a senha
        valores.put("senha", senha)

        // Adiciona o tipo de usuário
        valores.put("tipo", tipo)

        // Insere os dados na tabela usuários
        return db.insert("usuarios", null, valores)
    }


    // Publica um novo DDS
    fun publicarDDS(
        titulo: String,
        texto: String,
        arquivo: String?,
        data: String,
        idTst: Int
    ): Long {

        // Abre o banco para inserir dados
        val db = writableDatabase

        // Cria um conjunto de valores para inserir
        val valores = ContentValues()

        // Adiciona o título do DDS
        valores.put("titulo", titulo)

        // Adiciona o texto do DDS
        valores.put("texto", texto)

        // Adiciona o arquivo do DDS
        valores.put("arquivo", arquivo)

        // Adiciona a data de publicação
        valores.put("data_publicacao", data)

        // Adiciona o ID do TST
        valores.put("id_tst", idTst)

        // Insere o DDS na tabela
        return db.insert("dds", null, valores)
    }


    // Registra quando um colaborador visualiza um DDS
    fun registrarVisualizacao(
        idUsuario: Int,
        idDds: Int,
        data: String
    ): Long {

        // Abre o banco para inserir dados
        val db = writableDatabase

        // Cria um conjunto de valores para inserir
        val valores = ContentValues()

        // Adiciona o ID do colaborador
        valores.put("id_usuario", idUsuario)

        // Adiciona o ID do DDS
        valores.put("id_dds", idDds)

        // Adiciona a data da visualização
        valores.put("data_visualizacao", data)

        // Insere a visualização na tabela
        return db.insert("visualizacoes", null, valores)
    }


    // Registra a resposta do emociômetro
    fun registrarEmociometro(
        idUsuario: Int,
        idDds: Int,
        estado: String,
        data: String
    ): Long {

        // Abre o banco para inserir dados
        val db = writableDatabase

        // Cria um conjunto de valores para inserir
        val valores = ContentValues()

        // Adiciona o ID do colaborador
        valores.put("id_usuario", idUsuario)

        // Adiciona o ID do DDS
        valores.put("id_dds", idDds)

        // Adiciona o estado emocional
        valores.put("estado", estado)

        // Adiciona a data da resposta
        valores.put("data_registro", data)

        // Insere a resposta na tabela
        return db.insert("emociometro", null, valores)
    }
}

