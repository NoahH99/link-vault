import {ChangeEvent, FormEvent, useState} from "react";

interface ArrayItem {
    original_filename: string;
    filename: string;
    file_url: string;
    raw_file_size: number;
    file_size: string;

}

interface FileResponseArray extends Array<ArrayItem>{}

interface FileResponse {
    uploaded_files: FileResponseArray;
}


function UploadFile() {
    const [files, setFiles] = useState<FileList | null>(null)
    const [resp, setResp] = useState([<div></div>,''])


    const handleUploadFiles = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        console.log(event);
        console.log(files);
        if (files) {
            console.log(files[0]);
        }
        const formData = new FormData();
        const token = localStorage.getItem('jwtToken');
        
        if (files && files[0]) {
            for (let i = 0; i < files.length; i++) {
                formData.append('files', files[i])
            }
        }

        const response = await fetch('/api/file-storage/upload',
         {method: 'POST', headers: {'Authorization': 'Bearer '+token,
         }, body: formData}
        );

        if (response == null) {
            setResp(["File Upload Unsuccessful"])
            return
        }

        if (!response.ok) {
            setResp(["There was an error when attempting to upload files"])
            return
        }
    
        const a: FileResponse = await response.json()
        var respString = [];
        respString.push(<div><br/><br/></div>)
        for (let i = 0; i < a.uploaded_files.length; i++) {
            respString.push(a.uploaded_files[i].original_filename, ' stored as ', a.uploaded_files[i].filename, <div><br/><br/></div>)
        }
        console.log(a.uploaded_files[0].filename)
        setResp(respString)

    }

    const updateFiles = (event: ChangeEvent<HTMLInputElement>) => {
        setFiles(event.target.files);
    }

    return (
        <>
            <div>Select Files to Upload</div>
            <form onSubmit={handleUploadFiles}>
                <input type="file" id="upload" onChange={updateFiles} multiple/>
                <input type="submit"/>
            </form>
            <div>{resp}</div>
        </>
    );
}

export default UploadFile
