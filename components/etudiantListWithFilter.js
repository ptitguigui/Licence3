import React from 'react';

import EtudiantList from '../components/etudiantList.js';

export default class etudiantListWithFilter extends React.Component {
    constructor(props) {
        super(props);
        this.state = {etudiants: [], filterText: ''}
    }

    componentWillMount() {
        this.setState({etudiants: this.props.alletudiants});
    }


    changeFilter() {
        this.setState({filterText: this.filterInput.value});
    }

    filterList() {
        let list = this.state.etudiants;
        let filterText = this.state.filterText;

        if (filterText === "") {
            console.log("returning all");
            return list;
        }


        return list.filter(etudiant => {
            return etudiant.nom.toLowerCase().includes(filterText) || etudiant.prenom.toLowerCase().includes(filterText);
        });
    }

    render() {

        return (
            <div>
                Name or surname filter : <input
                value={this.state.filterText}
                onChange={this.changeFilter.bind(this)}
                ref={input => this.filterInput = input}
            />
                <EtudiantList
                    alletudiants={this.filterList()}
                />
            </div>
        );
    }
}