import { useState } from 'react';
function Uploader() {

    const handleUploadFiles = () => {
        
    }


    return (
        <>
            <div>Select Files to Upload</div>
            <form>
            <input type="file" id="upload" multiple name="filename"/>
            <input type="submit" onSubmit={ handleUploadFiles }/>
            </form>
            

        </>
    )
}

export default Uploader
