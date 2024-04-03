
import { FormEvent, ChangeEvent, useState } from 'react';
function Uploader() {

    const [files, setFiles] = useState<FileList | null>(null)

    const handleUploadFiles = (event:FormEvent<HTMLFormElement>) => {
        console.log(files);
    }

    const updateFiles = (event:ChangeEvent<HTMLInputElement>) => {
        setFiles(event.target.files);
    }


    return (
        <>
            <div>Select Files to Upload</div>
            <form onSubmit={ handleUploadFiles }>
            <input type="file" id="upload" onChange={ updateFiles } multiple/>
            <input type="submit"/>
            </form>
            

        </>
    )
}

export default Uploader

