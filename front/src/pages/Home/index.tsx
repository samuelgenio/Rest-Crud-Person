import React, { useState, ChangeEvent, FormEvent } from 'react';
import './styles.css';
import { useHistory } from 'react-router-dom';
import api from '../../services/api';
import logo from '../../assets/logo.svg';
const Home = () => {

    const history = useHistory();

    const [formData, setFormData] = useState({
        username: '',
        password: '',
    });

    function handleInputChange(event: ChangeEvent<HTMLInputElement>) {
        const { name, value } = event.target;
        setFormData({ ...formData, [name]: value });
    }

    async function handleOnsubmit(event: FormEvent) {
        event.preventDefault();

        const { username, password } = formData;

        await api.post(`api/v1/auth/signin`, {
            username,
            password
        }).then((response) => {

            if (response.status === 204) {
                alert("E-mail ou senha inválidos!");
            } else if (response.status === 200) {
                history.push('/person/' + response.data.token);
            }

        });
    }

    return (
        <div id="page-home">
            <div className="content">
                <header>
                    <img src={logo} alt="Softplan" id="logo" />
                </header>
                <main>

                    <div className="login">
                        <form action=""
                            onSubmit={handleOnsubmit}
                        >
                            <h1>
                                Cadastro de Pessoa
                            </h1>
                            <div className="field-group">
                                <div className="field">
                                    <label htmlFor="username">Username</label>
                                    <input
                                        type="username"
                                        name="username"
                                        id="username"
                                        onChange={handleInputChange}
                                    />
                                </div>
                                <div className="field">
                                    <label htmlFor="password">Password</label>
                                    <input
                                        type="password"
                                        name="password"
                                        id="password"
                                        onChange={handleInputChange}
                                    />
                                </div>

                            </div>
                            <button type="submit">Acessar</button>
                        </form>
                    </div>


                </main>
            </div>
        </div>
    )
}

export default Home;