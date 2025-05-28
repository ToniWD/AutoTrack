import '../styles/SideBar.css';
import { useState } from 'react';
import { EntityForm, type EntityType } from './GenericForm';
import '../styles/Modal.css';
import '../styles/Scrollbar.css'

const ModalForm: React.FC<{ entityType: EntityType, onClose: () => void }> = ({ entityType, onClose }) => {

    if (!entityType) return null;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={e => e.stopPropagation()}>
        <h2>Add {entityType}</h2>
        <EntityForm entityType={entityType} onSubmit={onClose}/>
        <button onClick={onClose}>Close</button>
      </div>
    </div>
  );
};

const SidebarMenu = () => {
    const [showModal, setShowModal] = useState(false);
    const [entityTypeForm, setEntityType] = useState<EntityType>("driver");

    return (
        <div className="nav-sidebar">
            <ul className="menu-list scrollable">
                <li>
                    <button className="menu-item">Dashboard</button>
                </li>
                <li>
                    <button className="menu-item" 
                    onClick = {() => {setEntityType("vehicle");setShowModal(true)}}
                    >Add new vehicle</button>
                </li>
                <li>
                    <button className="menu-item"
                    onClick = {() => {setEntityType("driver");setShowModal(true)}}
                    >Add new drivers</button>
                </li>
                <li>
                    <button className="menu-item">Settings</button>
                </li>
            </ul>

            {showModal && (
                    <ModalForm
                      entityType = {entityTypeForm}
                      onClose={() => setShowModal(false)}
                    />
            )}
        </div>
    );
};

export default SidebarMenu;
