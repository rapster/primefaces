/**
 * Copyright 2009-2017 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.component.fileupload;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.component.UINamingContainer;
import javax.el.ValueExpression;
import javax.el.MethodExpression;
import javax.faces.render.Renderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import java.util.List;
import java.util.ArrayList;
import org.primefaces.util.ComponentUtils;
import org.primefaces.cdk.annotations.*;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="fileupload/fileupload.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="core.js"),
	@ResourceDependency(library="primefaces", name="components.js"),
	@ResourceDependency(library="primefaces", name="fileupload/fileupload.js")
})
@PFComponent(tagName = "fileUpload",
             description = "FileUpload goes beyond the browser input type=\"file\" functionality and features a flash-javascript solution for uploading files. \n      File filtering, multiple uploads, partial page rendering and progress tracking are the significant features compared to legacy fileUploads.",
             widget = true,
             rtl = true)
public abstract class FileUploadCore extends UIInput implements IFileUpload {

	@PFPropertyKeys(base = {org.primefaces.component.api.propertykeys.UIInputPropertyKeys.class})
	public enum PropertyKeys {

		@PFProperty(description = "Inline style of the main component")
		style,
		@PFProperty(description = "Style class of the component")
		styleClass,
		@PFProperty(description = "Component(s) to be updated after fileupload completes")
		update,
		@PFProperty(description = "Component(s) to be processed during fileupload request")
		process,
		@PFProperty(description = "Method expression to listen file upload events", type = javax.el.MethodExpression.class)
		fileUploadListener,
		@PFProperty(description = "Allows choosing of multi file uploads from native file browse dialog, turned off by default", defaultValue = "false", type = Boolean.class)
		multiple,
		@PFProperty(description = "When set to true, selecting a file starts the upload process implicitly. Default is false", defaultValue = "false", type = Boolean.class)
		auto,
		@PFProperty(description = "Label of the browse button, default is 'Choose'", defaultValue = "Choose")
		label,
		@PFProperty(description = "Regular expression for accepted file types, e.g. /(\\.|\\/)(gif|jpe?g|png)$/")
		allowTypes,
		@PFProperty(description = "Maximum number of files to be uploaded", defaultValue = "java.lang.Integer.MAX_VALUE", type = Integer.class)
		fileLimit,
		@PFProperty(description = "Individual file size limit in bytes. Default is unlimited", defaultValue = "java.lang.Long.MAX_VALUE", type = Long.class)
		sizeLimit,
		@PFProperty(description = "Mode of the uploader, valid values are \"simple\" and \"advanced\"", defaultValue = "advanced")
		mode,
		@PFProperty(description = "Label of the upload button, default is 'Upload'", defaultValue = "Upload")
		uploadLabel,
		@PFProperty(description = "Label of the cancel button, default is 'Cancel'", defaultValue = "Cancel")
		cancelLabel,
		@PFProperty(description = "Message to display when file size validation fails")
		invalidSizeMessage,
		@PFProperty(description = "Message to display when file limit validation fails")
		invalidFileMessage,
		@PFProperty(description = "Message to display when file count validation fails")
		fileLimitMessage,
		@PFProperty(description = "Specifies dragdrop based file selection from filesystem, default is true and works only on supported browsers", defaultValue = "true", type = Boolean.class)
		dragDropSupport,
		@PFProperty(description = "Callback to execute at the beginning of fileupload")
		onstart,
		@PFProperty(description = "Callback to execute after fileupload request completes")
		oncomplete,
		@PFProperty(description = "Callback to execute after fileupload request fails")
		onerror,
		@PFProperty(description = "Disables fileupload user interface interaction", defaultValue = "false", type = Boolean.class)
		disabled,
		@PFProperty(description = "Message template to use when displaying file validation errors. Default is \"{name} {size}\"")
		messageTemplate,
		@PFProperty(description = "Width for image previews in pixels. Default value is 80", defaultValue = "80", type = Integer.class)
		previewWidth,
		@PFProperty(description = "Applies theming to simple uploader, default is false", defaultValue = "false", type = Boolean.class)
		skinSimple,
		@PFProperty(description = "To filter files in native file browser dialog")
		accept,
		@PFProperty(description = "Uploads are concurrent by default set this option to true for sequential uploads", defaultValue = "false", type = Boolean.class)
		sequential,;
	}

    public final static String CONTAINER_CLASS = "ui-fileupload ui-widget ui-fileupload-responsive";
    public final static String BUTTON_BAR_CLASS = "ui-fileupload-buttonbar ui-widget-header ui-corner-top";
    public final static String CONTENT_CLASS = "ui-fileupload-content ui-widget-content ui-corner-bottom";
    public final static String FILES_CLASS = "ui-fileupload-files";
    public final static String CHOOSE_BUTTON_CLASS = "ui-fileupload-choose";
    public final static String UPLOAD_BUTTON_CLASS = "ui-fileupload-upload";
    public final static String CANCEL_BUTTON_CLASS = "ui-fileupload-cancel";
    public final static String BUTTON_ICON_ONLY = "ui-fileupload-icon-only";
    
    public final static String CONTAINER_CLASS_SIMPLE = "ui-fileupload-simple ui-widget";
    public final static String FILENAME_CLASS = "ui-fileupload-filename";
    
    public final static String MOBILE_CONTAINER_CLASS = "ui-fileupload ui-input-text ui-body-inherit ui-corner-all ui-shadow-inset";

    public void broadcast(javax.faces.event.FacesEvent event) throws javax.faces.event.AbortProcessingException {
		super.broadcast(event);
		
		FacesContext facesContext = getFacesContext();
		MethodExpression me = getFileUploadListener();
		
		if (me != null && event instanceof org.primefaces.event.FileUploadEvent) {
			me.invoke(facesContext.getELContext(), new Object[] {event});
		}
	}
}