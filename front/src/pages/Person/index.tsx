import React, { Component } from 'react';
import './styles.css';
import { FiPlusCircle } from 'react-icons/fi';
import { Link } from 'react-router-dom';
import { RouteComponentProps } from 'react-router';
import api from '../../services/api';

import ListItem from '../../components/ListItem';

import { DataInterface } from '../../interfaces/Data';

import logo from '../../assets/logo.svg';

interface RouteParams { key: string, param2?: string }

class User extends Component<RouteComponentProps<RouteParams>> {

    state = {
        data: [
        ], key: null
    }

    constructor(props: any) {
        super(props);
    }

    componentDidMount() {
        this.setState({ key: this.props.match.params.key });
        this.loadUser();
    }

    loadUser() {

        const headers = {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',
            'Authorization': 'Bearer:' + this.props.match.params.key
        };
        api.get<DataInterface>(`api/v1/person`, {
            headers: headers
        }).then((response) => {
            if (response.status === 204) {
                alert("Dados não existem");
            } else if (response.status === 200) {
                this.setState({ data: response.data });
            }
        }).catch(function (error) {
            console.log(error)
        });
    }

    delete(id: any, bearer: any) {
        const headers = {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',
            'Authorization': 'Bearer:' + bearer
        };

        api.delete(`api/v1/person/${id}`, {
            headers: headers
        }).then((response) => {
            if (response.status === 200) {
                this.loadUser();
            }
        });

    }

    render() {
        return (

            <div id="page-create">
                <header>
                    <Link to="/">
                        <img src={logo} alt="Softplan" id="logo" />
                    </Link>
                    <Link className="link-button" to={'/person/create/' + this.state.key}>
                        <span><FiPlusCircle /></span>
                        <strong>Adicionar</strong>
                    </Link>
                </header>
                <div id="content-list">

                    <h1>Lista de Pessoas</h1>

                    <ol id="list-cabecalho">
                        <li>Nome</li>
                        <li>CPF</li>
                        <li>E-mail</li>
                        <li>Origem</li>
                        <li>Dat. Cadastro</li>
                        <li>Operações</li>
                    </ol>

                    {
                        this.state.key == null
                            ?
                            <p id="no-data-found">Nenhum registro encontrado :(</p>
                            :
                            <ul>
                                {
                                    this.state.data.map((data: DataInterface) => (
                                        <ListItem key={data.id}
                                            data={data}
                                            bearer={this.state.key}
                                            deleteHandle={() => this.delete(data.id, this.state.key)}
                                        />
                                    )
                                    )
                                }
                            </ul>
                    }
                </div>
            </div>
        )
    }


}

export default User;