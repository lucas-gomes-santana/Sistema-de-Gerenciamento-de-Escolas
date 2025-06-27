import '../styles/Login.css';

function Login() {
    return(
        <>
            <section className='login-section'>

                <div className="home-images">
                    <img className="background-image" src="https://champlainsaintlambert.ca/wp-content/uploads/2025/05/Untitled-design-43.jpg" alt="" />

                    <div className="students-images">
                        <h1>Bem vindo ao Sistema Escolar!</h1>


                        <img className='image-1' src="https://cdn.pixabay.com/photo/2014/03/12/18/45/boys-286245_960_720.jpg" alt="" />
                        <img className='image-2' src="https://cdn.pixabay.com/photo/2015/10/21/08/14/girl-998988_960_720.jpg" alt="" />
                    </div>
                </div>

                <form className="login-form" action="">
                    <h3>Acesse o Sistema:</h3>

                    <input placeholder="Nome de Usuário" type="text" />
                    <input placeholder="Senha" type="password" />

                    <button className='login-button'>Login</button>
                </form>

            </section>
        </>
    );
}

export default Login;