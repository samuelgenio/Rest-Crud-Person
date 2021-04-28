import React from 'react';
import { FiEdit } from 'react-icons/fi';
import { FiTrash } from 'react-icons/fi';
import { Link } from 'react-router-dom';

import { DataInterface } from '../interfaces/Data';

const ListItem = ({ data, bearer, deleteHandle }: { data: DataInterface, bearer: any, deleteHandle: any }) => {

  function dateFormat(value: string) {
    const replaced = value.split("-");
    const replaceTime = replaced[2].split(" ");
    return replaceTime[0] + "/" + replaced[1] + "/" + replaced[0] + ' ' + replaceTime[1];
  }

  return (

    <li id="list-itens">
      <span className="Item-field">
        {data.name}
      </span>
      <span className="Item-field">
        {data.cpf}
      </span>
      <span className="Item-field">
        {data.mail}
      </span>
      <span className="Item-field">
        {data.naturalness}
      </span>
      <span className="Item-field">
        {dateFormat(data.registrationDate)}
      </span>
      <span className="container-operations">
        <Link className="link-button operation" to={'/person/update/' + data.id + "/" + bearer}>
          <span><FiEdit /></span>
        </Link>
        <button className="link-button operation" onClick={deleteHandle}><span><FiTrash /></span></button>
      </span>
    </li>
  );
};

export default ListItem;