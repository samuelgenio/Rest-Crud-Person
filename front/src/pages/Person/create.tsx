import React, { ChangeEvent, Fragment } from 'react';
import './styles.css';
import { Link } from 'react-router-dom';
import { RouteComponentProps } from 'react-router';
import axios from 'axios';
import api from '../../services/api';
import logo from '../../assets/logo.svg';
import * as yup from 'yup'
import { Formik } from 'formik';
import InputMask from 'react-input-mask';

import { DataInterface } from '../../interfaces/Data';

interface IBGEUFResponse {
    sigla: string;
}

interface IBGECityResponse {
    nome: string;
}

interface RouteParams { id: string, key?: string }

class PersonCreate extends React.Component<RouteComponentProps<RouteParams>> {

    state = {
        person: {
            id: 0,
            name: "",
            gender: "",
            mail: "",
            birthDate: "",
            naturalness: "",
            nationality: "",
            cpf: "",
            registration_date: "",
            change_date: "",
        },
        ufs: [],
        cities: [],
        selectedUf: '',
        selectedCity: '',
        selectedGender: 'F',
        personId: 0,
        key: '',
        apiV: 1,
    }

    componentDidMount() {

        
        if (this.props.match.params.id) {
            this.loadPerson(this.props.match.params.id);
        } else {
            this.setState({ personId: -1 });
        }

        this.setState({ key: this.props.match.params.key });


        axios.get<IBGEUFResponse[]>('https://servicodados.ibge.gov.br/api/v1/localidades/estados').then(response => {
            const ufInitials = response.data.map(uf => uf.sigla);
            this.setState({
                ufs: ufInitials,
            });
        });

    }

    loadPerson(id: string) {

        const headers = {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',
            'Authorization': 'Bearer:' + this.props.match.params.key
        };

        api.get<DataInterface>(`api/v1/person/${id}`, {
            headers: headers
        }).then((response) => {
            if (response.status === 204) {
                alert("Pessoa não existe.");
                this.props.history.goBack();
            } else if (response.status === 200) {

                var partsDate = response.data.birthDate.split('-');

                response.data.birthDate = partsDate[2] + '/' + partsDate[1] + '/' + partsDate[0];

                this.setState({
                    person: response.data
                });

                this.setState({
                    selectedGender: response.data.gender.substr(0, 1),
                });

                var exploded = response.data.naturalness.split(" - ");

                this.setState({
                    selectedUf: exploded[0],
                });

                this.handleSelectUf(null);

                this.setState({
                    selectedCity: exploded[1],
                });

                this.setState({ personId: this.props.match.params.id });

            }

        }, (error) => {
        });
    }

    handleSelectUf(event: any) {

        let uf = this.state.selectedUf;

        if (event != null) {
            uf = event.target.value;
            this.setState({
                selectedUf: event.target.value,
            });
        }
        if (uf === '0') {
            return;
        }

        axios.get<IBGECityResponse[]>(`https://servicodados.ibge.gov.br/api/v1/localidades/estados/${uf}/municipios`).then(response => {
            const cityNames = response.data.map(city => city.nome);
            this.setState({
                cities: cityNames,
            });
        });
    };

    handleSelectCity(event: ChangeEvent<HTMLSelectElement>) {
        this.setState({
            selectedCity: event.target.value,
        });
    };

    handleSelectGender(event: ChangeEvent<HTMLSelectElement>) {
        this.setState({
            selectedGender: event.target.value,
        });
    };

    yupValidate() {
        return yup.object().shape({
            name: yup
                .string()
                .max(60, 'Nome deve ter no máximo 60 caracteres!')
                .required('Nome é Obrigatório!'),
            mail: yup
                .string()
                .email('E-mail inválido'),
            birthDate: yup
                .string()
                .required('Data de Nascimento é Obrigatório!'),
            cpf: yup
                .string()
                .required('CPF é Obrigatório!')
        })
    }

    async handleOnsubmit(values: any) {

        var partsDate = values.birthDate.split('/');

        var naturalness = '';

        if (this.state.selectedUf != ''
            && this.state.selectedCity != '') {
            naturalness = this.state.selectedUf != '' ? this.state.selectedUf : '';
            naturalness += ' - ';
            naturalness += this.state.selectedCity != '' ? this.state.selectedCity : '';
        }

        const headers = {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept',
            'Authorization': 'Bearer:' + this.state.key
        };

        if (this.state.personId > 0) {
            await api.put(`api/v${this.state.apiV}/person/${this.state.personId}`, {
                'name': values.name,
                'gender': this.state.selectedGender,
                'mail': values.mail,
                'birthDate': partsDate[2] + '-' + partsDate[1] + '-' + partsDate[0],
                'nationality': values.nationality,
                'cpf': this.state.person.cpf,
                'naturalness': naturalness
            }, {
                headers: headers
            })
                .then((response) => {
                    alert("Pessoa alterada com sucesso!");
                    this.props.history.push("/person/" + this.state.key);
                }).catch(function (error) {
                    if (error.response) {
                        alert("Falha ao alterar. " + error.response.data.message);
                    }
                });
        } else {
            await api.post(`api/v${this.state.apiV}/person`, {
                'name': values.name,
                'gender': this.state.selectedGender,
                'mail': values.mail,
                'birthDate': partsDate[2] + '-' + partsDate[1] + '-' + partsDate[0],
                'nationality': values.nationality,
                'cpf': values.cpf,
                'naturalness': naturalness
            }, {
                headers: headers
            })
                .then((response) => {
                    alert("Pessoa cadastrada com sucesso!");
                    this.props.history.push("/person/" + this.state.key);
                }).catch(function (error) {
                    if (error.response) {

                        alert("Falha ao cadastrar. " + error.response.data.message);
                    }
                });
        }
    }

    render() {
        return (

            <div id="page-create">
                <header>
                    <Link to="/">
                        <img src={logo} alt="Softplan" id="logo" />
                    </Link>
                </header>
                <div id="content-form">
                    {
                        this.state.personId == 0
                            ?
                            <p>carregando</p>

                            :

                            <Formik
                                initialValues={{
                                    name: this.state.person?.name,
                                    mail: this.state.person?.mail,
                                    birthDate: this.state.person?.birthDate,
                                    nationality: this.state.person?.nationality,
                                    cpf: this.state.person?.cpf
                                }}
                                onSubmit={values => this.handleOnsubmit(values)}
                                validationSchema={this.yupValidate}
                            >
                                {({ values, handleChange, errors, setFieldTouched, touched, isValid, handleSubmit }) => (
                                    <Fragment>

                                        {
                                            this.state.personId <= 0
                                                ?
                                                <h1>Cadastro</h1>
                                                :
                                                <h1>Alteração</h1>
                                        }

                                        <fieldset>
                                            <legend>
                                                <h2>Geral</h2>
                                            </legend>

                                            <div className="field">

                                                <label htmlFor="name">Nome Completo</label>

                                                <input
                                                    type="text"
                                                    name="name"
                                                    id="name"
                                                    value={values.name}
                                                    onChange={handleChange('name')}
                                                    onBlur={() => setFieldTouched('name')}
                                                />
                                                {touched.name && errors.name &&
                                                    <p className="error-info">{errors.name}</p>
                                                }
                                            </div>

                                            <div className="field-group">

                                                <div className="field">
                                                    <label htmlFor="cpf">CPF</label>

                                                    {
                                                        this.state.personId <= 0
                                                            ?
                                                            <InputMask name="cpf"
                                                                id="cpf"
                                                                mask="999.999.999-99"
                                                                value={values.cpf}
                                                                onChange={handleChange('cpf')}
                                                                onBlur={() => setFieldTouched('cpf')}
                                                            />
                                                            :
                                                            <input
                                                                type="cpf"
                                                                name="cpf"
                                                                id="cpf"
                                                                value={values.cpf}
                                                                disabled={this.state.personId > 0}
                                                            />
                                                    }
                                                    {touched.cpf && errors.cpf &&
                                                        <p className="error-info">{errors.cpf}</p>
                                                    }
                                                </div>

                                                <div className="field">
                                                    <label htmlFor="birthDate">Data de Nascimento</label>
                                                    <InputMask mask="99/99/9999" name="birthDate"
                                                        id="birthDate"
                                                        value={values.birthDate}
                                                        onChange={handleChange('birthDate')}
                                                        onBlur={() => setFieldTouched('birthDate')} />

                                                    {touched.birthDate && errors.birthDate &&
                                                        <p className="error-info">{errors.birthDate}</p>
                                                    }
                                                </div>

                                            </div>

                                            <div className="field-group">

                                                <div className="field">
                                                    <label htmlFor="gender">Sexo</label>
                                                    <select name="gender" id="gender" value={this.state.selectedGender} onChange={(event) => this.handleSelectGender(event)}>
                                                        <option value="F">Feminino</option>
                                                        <option value="M">Masculino</option>
                                                    </select>
                                                </div>

                                                <div className="field">
                                                    <label htmlFor="mail">E-mail</label>
                                                    <input
                                                        type="mail"
                                                        name="mail"
                                                        id="mail"
                                                        value={values.mail}
                                                        onChange={
                                                            handleChange('mail')
                                                        }
                                                        onBlur={() => setFieldTouched('mail')}
                                                    />
                                                    {touched.mail && errors.mail &&
                                                        <p className="error-info">{errors.mail}</p>
                                                    }
                                                </div>

                                            </div>

                                        </fieldset>

                                        <fieldset>
                                            <legend>
                                                <h2>Endereço</h2>
                                            </legend>

                                            <div className="field">
                                                <label htmlFor="nationality">Nacionalidade</label>
                                                <input
                                                    type="nationality"
                                                    name="nationality"
                                                    id="nationality"
                                                    value={values.nationality}
                                                    onChange={
                                                        handleChange('nationality')
                                                    }
                                                    onBlur={() => setFieldTouched('nationality')}
                                                />
                                                {touched.nationality && errors.nationality &&
                                                    <p className="error-info">{errors.nationality}</p>
                                                }
                                            </div>

                                            <div className="field-group">
                                                <div className="field">
                                                    <label htmlFor="uf">Estado (UF)</label>
                                                    <select name="uf" id="uf" value={this.state.selectedUf} onChange={(event) => this.handleSelectUf(event)}>
                                                        <option value="0">Selecione um Estado</option>
                                                        {
                                                            this.state.ufs.map(uf => <option key={uf} value={uf}>{uf}</option>)
                                                        }
                                                    </select>
                                                </div>
                                                <div className="field">
                                                    <label htmlFor="cidade">Cidade</label>
                                                    <select name="cidade" id="cidade" value={this.state.selectedCity} onChange={(event) => this.handleSelectCity(event)}>
                                                        <option value="0">Selecione uma Cidade</option>
                                                        {
                                                            this.state.cities.map(city => <option key={city} value={city}>{city}</option>)
                                                        }
                                                    </select>
                                                </div>

                                            </div>

                                        </fieldset>

                                        <button type="submit" onClick={e => handleSubmit()} disabled={!isValid}>{this.state.personId > 0 ? "Alterar" : "Cadastrar"}</button>

                                    </Fragment>
                                )}
                            </Formik>

                    }
                </div>
            </div>
        )
    }


}

export default PersonCreate;