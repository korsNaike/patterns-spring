import React from 'react';
import StudentTable from './components/StudentsTable';
import {ToastContainer} from 'react-toastify'

const App: React.FC = () => {
    return (
        <div>
            <h1>Таблица студентов</h1>
            <StudentTable />
            <ToastContainer />
        </div>
    );
};

export default App;