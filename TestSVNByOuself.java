import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.tmatesoft.svn.cli.svn.SVNCommand;
import org.tmatesoft.svn.cli.svn.SVNNotifyPrinter;
import org.tmatesoft.svn.cli.svn.SVNOption;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNErrorCode;
import org.tmatesoft.svn.core.SVNErrorMessage;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.SVNPath;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.ISVNStatusHandler;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNCommitPacket;
import org.tmatesoft.svn.core.wc.SVNConflictChoice;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNStatusType;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;


public class TestSVNByOuself extends SVNCommand {

	protected TestSVNByOuself() {
		super("resolved", null); 

	}
	protected Collection createSupportedOptions() { 
		Collection options = new LinkedList(); 
		options.add(SVNOption.TARGETS); 
		options.add(SVNOption.RECURSIVE); 
		options.add(SVNOption.DEPTH); 
		options.add(SVNOption.QUIET); 
		return options; 
	} 

	public static void main(String[] args) throws SVNException, IOException{
		TestSVNByOuself t=new TestSVNByOuself();
		//  SVNClientManager clientManager= t.getSVNClientManager();
		// System.out.println("This is client manager object------------"+clientManager);
		t.commitToSvn();
		//t.readFile(clientManager);
	}//end of main

	//------******method to actual commit*****-----
	public void commitToSvn()throws SVNException, IOException 
	{
		TestSVNByOuself t=new TestSVNByOuself();
		File f=new File("/home/shikha/workspace3/testproj2/testproj2/");
		System.out.println("this is our file location"+f.getAbsolutePath());
		final SVNURL url=SVNURL.parseURIEncoded("http://192.168.2.200:8800/svn/buzzor/trunk/development/tteesstt/testproj2/");
		// final SVNURL url = SVNURL.parseURIEncoded(masterRepoLocation);
		final SVNRepository repository = SVNRepositoryFactory.create(url);
		System.out.println("this is our repository "+repository);
		final ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager("rushikesh.jeware","rushikesh.jeware");
		repository.setAuthenticationManager(authManager);
		final SVNClientManager ourClientManager = SVNClientManager.newInstance();
		ourClientManager.setAuthenticationManager(authManager);
		final SVNUpdateClient uc = ourClientManager.getUpdateClient();
		final long latestRevision = repository.getLatestRevision();
		System.out.println("latest version of our repository"+latestRevision);
		uc.doCheckout(url, f, SVNRevision.UNDEFINED, SVNRevision.parse("" + latestRevision), SVNDepth.UNKNOWN, false);
		System.out.println("Adding all files in repository");
		//SVNWCClient client =getSVNClientManager().getWCClient();
		//System.out.println(client);
		t.run();
		ourClientManager.getWCClient().doAdd(f, true, true, true, SVNDepth.INFINITY, true, true);
		System.out.println("Commit Operation is  Done");
		//SVNCommitClient commitClient = clientManager.getCommitClient();
	}

	public void run() throws SVNException { 
		final SVNURL url=SVNURL.parseURIEncoded("http://192.168.2.200:8800/svn/buzzor/trunk/development/tteesstt/testproj2/");
		final SVNRepository repository = SVNRepositoryFactory.create(url);
		final ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager("rushikesh.jeware","rushikesh.jeware");
		repository.setAuthenticationManager(authManager);
		final SVNClientManager ourClientManager = SVNClientManager.newInstance();
		ourClientManager.setAuthenticationManager(authManager);
		//final SVNClientManager ourClientManager = SVNClientManager.newInstance();
		//ourClientManager.setAuthenticationManager(authManager);
		//List targets = getSVNEnvironment().combineTargets(getSVNEnvironment().getTargets(), true); 
		//        System.out.println("we r in run method");

		//        if (targets.isEmpty()) { 
		//SVNErrorManager.error(SVNErrorMessage.create(SVNErrorCode.CL_INSUFFICIENT_ARGS), SVNLogType.CLIENT); 
		//           System.out.println("SVNErrorMessage.create(SVNErrorCode.CL_INSUFFICIENT_ARGS), SVNLogType.CLIENT");     
		//        } 
		// Map<SVNStatusType, ArrayList<String>> result = new HashMap<>();
		final List<String> targets = new ArrayList<String>();
		try {
			String dirPath="/home/shikha/workspace3/testproj2/";
			ourClientManager.getStatusClient().doStatus(new File(dirPath+"/testproj2"), SVNRevision.HEAD, SVNDepth.INFINITY, true, true, true, true, new ISVNStatusHandler() {
				@Override
				public void handleStatus(SVNStatus status) throws SVNException {
					targets.add(status.getNodeStatus() + " : " + status.getFile().getAbsolutePath());
					//System.out.println(""+targets);
//					if (status.getNodeStatus() == SVNStatusType.STATUS_UNVERSIONED) {
//						targetsunversioned.add(SVNStatusType.STATUS_UNVERSIONED + " : " + status.getFile().getAbsolutePath());
//					} else if (status.getNodeStatus() == SVNStatusType.STATUS_MODIFIED) {
//						targetsmodified.add(SVNStatusType.STATUS_MODIFIED + " : " + status.getFile().getAbsolutePath());
//					} else if (status.getNodeStatus() == SVNStatusType.STATUS_CONFLICTED) {
//						System.out.println("there is file in conflict");
//						targetsconflict.add(SVNStatusType.STATUS_CONFLICTED + " : " + status.getFile());
//					}
				}
			}, new ArrayList<String>());
		} catch (SVNException e) {
			e.printStackTrace();
		}
			
	for (String string : targets) {
			System.out.println(string);
		}
//		for (String string : targetsmodified) {
//		System.out.println(string);
//		}
//		for (String string : targetsunversioned) {
//			System.out.println(string);
//		}
		

//				SVNWCClient client = ourClientManager.getWCClient(); 
//				if (!getSVNEnvironment().isQuiet()) { 
//					client.setEventHandler(new SVNNotifyPrinter(getSVNEnvironment())); 
//				} 
//				SVNDepth depth = getSVNEnvironment().getDepth(); 
//				if (depth == SVNDepth.UNKNOWN) { 
//					depth = SVNDepth.EMPTY; 
//				} 
//				for (Iterator ts = targets.iterator(); ts.hasNext();) { 
//					String targetName = (String) ts.next(); 
//					SVNPath target = new SVNPath(targetName); 
//					if (target.isFile()) { 
//						try { 
//							client.doResolve(target.getFile(), depth, SVNConflictChoice.THEIRS_FULL); 
//						} catch (SVNException e) { 
//							SVNErrorMessage err = e.getErrorMessage(); 
//							getSVNEnvironment().handleWarning(err, new SVNErrorCode[] {err.getErrorCode()}, getSVNEnvironment().isQuiet()); 
//						} 
//					} 
//				} 
	} 

	//method to get client object//clientManager will be used to get different kind of svn clients instances to do different activities
	//like update, commit, view diff etc.	
	//	public SVNClientManager getSVNClientManager () throws IOException, SVNException{
	//		final String userName="rushikesh.jeware";
	//		final  String password="rushikesh.jeware";
	//		SVNURL url = SVNURL.parseURIEncoded("http://192.168.2.200:8800/svn/buzzor/trunk/development/tteesstt/testproj2/");                    
	//		SVNRepository repository = SVNRepositoryFactory.create(url);
	//		ISVNOptions myOptions = SVNWCUtil.createDefaultOptions(true);
	//		ISVNAuthenticationManager myAuthManager = SVNWCUtil.createDefaultAuthenticationManager(userName,password);
	//		repository.setAuthenticationManager(myAuthManager);
	//		SVNClientManager clientManager = SVNClientManager.newInstance(myOptions, myAuthManager);
	//				return clientManager ;
	//	} 
	//
	//	
	////-----------------method to read files from repository-----------------//
	//	public void readFile()
	//	{
	//		Map<SVNStatusType, ArrayList<String>> result = new HashMap<>();
	//		final List<String> lst = new ArrayList<String>();
	//		try {
	//			File f=new File("/home/shikha/workspace/testproj2/");
	//			  //System.out.println("this is our file location"+f.getAbsolutePath());
	//			  final SVNURL url=SVNURL.parseURIEncoded("http://192.168.2.200:8800/svn/buzzor/trunk/development/tteesstt/testproj2/");
	//			  // final SVNURL url = SVNURL.parseURIEncoded(masterRepoLocation);
	//			  final SVNRepository repository = SVNRepositoryFactory.create(url);
	//			  // System.out.println("this is our repository "+repository);
	//			  final ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager("rushikesh.jeware","rushikesh.jeware");
	//			  repository.setAuthenticationManager(authManager);
	//			  final SVNClientManager ourClientManager = SVNClientManager.newInstance();
	//			  ourClientManager.setAuthenticationManager(authManager);
	//			  System.out.println("we r in read method");
	//			  ourClientManager.getStatusClient().doStatus(new File(f+"/testproj2"), SVNRevision.HEAD, SVNDepth.INFINITY, true, true, true, true, new ISVNStatusHandler() {
	//
	//				   HashMap<SVNStatusType, ArrayList<String>> result = new
	//				   HashMap<>();
	//				   HashSet<String> conflicts = new HashSet<>();
	//                  
	//				@Override
	//				public void handleStatus(SVNStatus status) throws SVNException {
	//System.out.println("we r in handle status method");
	//					lst.add(status.getNodeStatus() + " : " + status.getFile().getAbsolutePath());
	//					if (status.getNodeStatus() == SVNStatusType.STATUS_UNVERSIONED) {
	//						lst.add(SVNStatusType.STATUS_UNVERSIONED + " : " + status.getFile().getAbsolutePath());
	//				} else if (status.getNodeStatus() == SVNStatusType.STATUS_MODIFIED) {
	//						lst.add(SVNStatusType.STATUS_MODIFIED + " : " + status.getFile().getAbsolutePath());
	//					} else if (status.getNodeStatus() == SVNStatusType.STATUS_CONFLICTED) {
	//						lst.add(SVNStatusType.STATUS_CONFLICTED + " : " + status.getFile());
	//					}
	//				}
	//			}, new ArrayList<String>());
	//		} catch (SVNException e) {
	//			e.printStackTrace();
	//		}
	//
	//		// for (Entry<SVNStatusType, ArrayList<String>> x : result.entrySet()) {
	//		// System.out.println(x.getKey().toString() + " - " + x.getValue() +
	//		// "\n");
	//		// }
	//		for (String string : lst) {
	//			System.out.println(string);
	//		}
	//		// return sb.toString();
	//	}


}//end of class



